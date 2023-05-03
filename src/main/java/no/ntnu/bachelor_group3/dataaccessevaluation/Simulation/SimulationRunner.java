package no.ntnu.bachelor_group3.dataaccessevaluation.Simulation;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Customer;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.dataaccessevaluation.Runnables.AddShipmentsRunnable;
import no.ntnu.bachelor_group3.dataaccessevaluation.Runnables.UpdateShipmentRunnable;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.*;
import org.apache.commons.lang3.time.StopWatch;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.*;

public class SimulationRunner {

    private ShipmentService shipmentService;
    private CustomerService customerService;
    private TerminalService terminalService;
    private ValidPostalCodeService validPostalCodeService;
    private ParcelService parcelService;
    private CheckpointService checkpointService;


    private ExecutorService executor1 = Executors.newFixedThreadPool(5);
    private ExecutorService executor2 = Executors.newFixedThreadPool(5);
    private ScheduledExecutorService executor3 = Executors.newScheduledThreadPool(5);

    private final ArrayBlockingQueue<Shipment> queue = new ArrayBlockingQueue<>(10000);


    public SimulationRunner(ShipmentService shipmentService, CustomerService customerService, TerminalService terminalService, ValidPostalCodeService validPostalCodeService, ParcelService parcelService, CheckpointService checkpointService) {
        this.shipmentService = shipmentService;
        this.customerService = customerService;
        this.terminalService = terminalService;
        this.validPostalCodeService = validPostalCodeService;
        this.parcelService = parcelService;
        this.checkpointService = checkpointService;
    }

    public void work() {

        Customer sender = new Customer("Ålesund", "Jonas", "6008");
        Customer receiver = new Customer("Oslo", "Tarjei", "0021");

        customerService.save(sender);
        customerService.save(receiver);

        System.out.println("Starting simulation...");
        for (int i = 0; i < 500; i++) {
            Shipment shipment = new Shipment(sender, sender, receiver);
            AddShipmentsRunnable shipmentsRunnable = new AddShipmentsRunnable(shipment, shipmentService);
            try {
                queue.put(shipment);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            executor1.execute(shipmentsRunnable);
        }



        executor1.shutdown();


        try {
            executor1.awaitTermination(2, TimeUnit.MINUTES);
            System.out.println("Add shipments done");
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Error in await termination");
        }
        int i = 0;
        var size = queue.size();
        while (i < size) {
            try {
                executor2.execute(new UpdateShipmentRunnable(queue.take(), shipmentService, terminalService));
                i++;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }



        executor2.shutdown();

        try {
            executor2.awaitTermination(2, TimeUnit.MINUTES);
            System.out.println("Update shipments done");
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Error in await termination");
        }

        System.out.println("Shipment location: " + shipmentService.getLocation(shipmentService.findByID(1L)));

        System.out.println("shipments: " + sender.getShipments().size());


        System.out.println("queue size: " + queue.size());
        System.out.println("shipments: " + shipmentService.count());
        System.out.println("parcels: " + parcelService.count());
        System.out.println("checkpoints: " + checkpointService.count());

        System.out.println("Number of shipments in terminal 14: " + terminalService.returnTerminalFromZip("6008").getShipmentNumber());
        System.out.println("Number of checkpoints in terminal 14: " + terminalService.returnTerminalFromZip("0021").getCheckpointNumber());


    }


    public void realTimeConverter() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Calendar calendar = Calendar.getInstance();
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        String[] daysOfWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        long previousElapsedSeconds = -1;
        while (stopWatch.getTime() < 30000) { // 30 seconds in milliseconds

            long elapsedSeconds = stopWatch.getTime();
            stopWatch.split();
            if (elapsedSeconds != previousElapsedSeconds) {
                if (((elapsedSeconds / 1000) != (previousElapsedSeconds / 1000)) && calendar.get(Calendar.DAY_OF_MONTH) != currentDay) {
                    previousElapsedSeconds = stopWatch.getSplitTime();
                    currentDay = calendar.get(Calendar.DAY_OF_MONTH);
                    int dayOfWeek = getDayOfWeek(calendar.getTime());
                    System.out.println("Today is " + daysOfWeek[dayOfWeek - 1]);
                } else {
                    previousElapsedSeconds = elapsedSeconds;
                }
            }
            calendar.setTime(new Date(stopWatch.getSplitTime() * 24 * 60 * 60 * 1000)); // Set the calendar time based on the elapsed time
            stopWatch.unsplit();

        }
        stopWatch.stop();

    }

    private static int getDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }
}









