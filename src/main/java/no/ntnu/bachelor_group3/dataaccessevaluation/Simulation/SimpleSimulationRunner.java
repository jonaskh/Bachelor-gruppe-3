package no.ntnu.bachelor_group3.dataaccessevaluation.Simulation;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Customer;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.dataaccessevaluation.Runnables.AddShipmentsRunnable;
import no.ntnu.bachelor_group3.dataaccessevaluation.Runnables.FindShipmentRunnable;
import no.ntnu.bachelor_group3.dataaccessevaluation.Runnables.TerminalShipmentsRunnable;
import no.ntnu.bachelor_group3.dataaccessevaluation.Runnables.UpdateShipmentRunnable;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.*;
import org.apache.commons.lang3.time.StopWatch;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class SimpleSimulationRunner {

    private ShipmentService shipmentService;
    private CustomerService customerService;
    private TerminalService terminalService;
    private ValidPostalCodeService validPostalCodeService;
    private ParcelService parcelService;
    private CheckpointService checkpointService;
    private static List<String> evals = new CopyOnWriteArrayList<>();



    private ExecutorService executor1 = Executors.newFixedThreadPool(3);
    private ExecutorService updateShipmentsService = Executors.newFixedThreadPool(10);
    private ScheduledExecutorService findShipmentInCustomerService = Executors.newScheduledThreadPool(10);
    private ScheduledExecutorService findShipmentsInTerminalService = Executors.newScheduledThreadPool(2);


    private final ArrayBlockingQueue<Shipment> queue = new ArrayBlockingQueue<>(10000);


    public SimpleSimulationRunner(ShipmentService shipmentService, CustomerService customerService, TerminalService terminalService, ValidPostalCodeService validPostalCodeService, ParcelService parcelService, CheckpointService checkpointService) {
        this.shipmentService = shipmentService;
        this.customerService = customerService;
        this.terminalService = terminalService;
        this.validPostalCodeService = validPostalCodeService;
        this.parcelService = parcelService;
        this.checkpointService = checkpointService;
    }

    public void simulate() {

        Customer sender = new Customer("Ålesund", "Jonas", "6008");
        Customer receiver = new Customer("Oslo", "Tarjei", "0021");

        customerService.save(sender);
        customerService.save(receiver);

        System.out.println("Starting simulation...");

        for (int i = 0; i < 10000; i++) {
            Shipment shipment = new Shipment(sender, sender, receiver);
            AddShipmentsRunnable shipmentsRunnable = new AddShipmentsRunnable(shipment, shipmentService, customerService);
            try {
                queue.put(shipment);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            executor1.execute(shipmentsRunnable);
        }
        try {
            //stops the executors from accepting new tasks
            updateShipmentsService.shutdown();
            findShipmentsInTerminalService.shutdown();
            findShipmentInCustomerService.shutdown();
            executor1.shutdown();


            //stops the thread pools if no more tasks, an exception occurs or timeout.
            updateShipmentsService.awaitTermination(2, TimeUnit.MINUTES);
            System.out.println("Update shipments done");

            findShipmentInCustomerService.awaitTermination(2, TimeUnit.MINUTES);
            findShipmentsInTerminalService.awaitTermination(2, TimeUnit.MINUTES);
            executor1.awaitTermination(2, TimeUnit.MINUTES);
            System.out.println("Adding done");


        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Error in await termination");
        }

        //evals.addAll(shipmentService.getShipmentEvals());
        //evals.addAll(parcelService.getParcelEvals());
        evals.addAll(customerService.getCustomerEval());
        //evals.addAll(checkpointService.getCheckpointEvals());

        evals.forEach(System.out::println);

    }
}

//
//    public void realTimeConverter() {
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
//        Calendar calendar = Calendar.getInstance();
//        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
//        String[] daysOfWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
//        long previousElapsedSeconds = -1;
//        while (stopWatch.getTime() < 30000) { // 30 seconds in milliseconds
//
//            long elapsedSeconds = stopWatch.getTime();
//            stopWatch.split();
//            if (elapsedSeconds != previousElapsedSeconds) {
//                if (((elapsedSeconds / 1000) != (previousElapsedSeconds / 1000)) && calendar.get(Calendar.DAY_OF_MONTH) != currentDay) {
//                    previousElapsedSeconds = stopWatch.getSplitTime();
//                    currentDay = calendar.get(Calendar.DAY_OF_MONTH);
//                    int dayOfWeek = getDayOfWeek(calendar.getTime());
//                    System.out.println("Today is " + daysOfWeek[dayOfWeek - 1]);
//                } else {
//                    previousElapsedSeconds = elapsedSeconds;
//                }
//            }
//            calendar.setTime(new Date(stopWatch.getSplitTime() * 24 * 60 * 60 * 1000)); // Set the calendar time based on the elapsed time
//            stopWatch.unsplit();
//
//        }
//        stopWatch.stop();
//
//    }

//    private static int getDayOfWeek(Date date) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        return calendar.get(Calendar.DAY_OF_WEEK);
//    }
//}









