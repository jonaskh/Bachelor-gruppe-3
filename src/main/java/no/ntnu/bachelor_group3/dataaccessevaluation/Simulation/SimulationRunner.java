package no.ntnu.bachelor_group3.dataaccessevaluation.Simulation;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Customer;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.dataaccessevaluation.Runnables.AddShipmentsRunnable;
import no.ntnu.bachelor_group3.dataaccessevaluation.Runnables.FindShipmentRunnable;
import no.ntnu.bachelor_group3.dataaccessevaluation.Runnables.TerminalShipmentsRunnable;
import no.ntnu.bachelor_group3.dataaccessevaluation.Runnables.UpdateShipmentRunnable;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.*;
import org.apache.commons.lang3.time.StopWatch;
import org.hibernate.sql.Update;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class SimulationRunner {

    private ShipmentService shipmentService;
    private CustomerService customerService;
    private TerminalService terminalService;
    private ValidPostalCodeService validPostalCodeService;
    private ParcelService parcelService;
    private CheckpointService checkpointService;
    private static List<String> evals = new CopyOnWriteArrayList<>();



    private ExecutorService executor1 = Executors.newFixedThreadPool(10);
    private ExecutorService updateShipmentsService = Executors.newFixedThreadPool(10);
    private ScheduledExecutorService findShipmentInCustomerService = Executors.newScheduledThreadPool(10);
    private ScheduledExecutorService findShipmentsInTerminalService = Executors.newScheduledThreadPool(2);


    private final ArrayBlockingQueue<Shipment> queue = new ArrayBlockingQueue<>(10000);


    public SimulationRunner(ShipmentService shipmentService, CustomerService customerService, TerminalService terminalService, ValidPostalCodeService validPostalCodeService, ParcelService parcelService, CheckpointService checkpointService) {
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

        for (int i = 0; i < 500; i++) {
            executor1.execute(new UpdateShipmentRunnable(new Shipment(sender, sender, receiver), shipmentService, terminalService));
        }



        //run find shipment location after a 0.5 second delay every second to simulate higher load.
        for (int k = 0; k < 10; k++) {
            findShipmentInCustomerService.scheduleAtFixedRate(new FindShipmentRunnable(shipmentService, customerService, shipmentService.findByID(1L)), 0, 500, TimeUnit.MILLISECONDS);
        }

        //run find shipments in terminal every second to simulate higher load.
        for (int j = 0; j < 10; j++) {
            findShipmentsInTerminalService.scheduleAtFixedRate(new TerminalShipmentsRunnable(shipmentService, terminalService, terminalService.returnTerminalFromZip("6300")), 500, 10000, TimeUnit.MILLISECONDS);
        }


//        //TODO: queue.take,
//        int i = 0;
//        var size = queue.size();
//        while (i < size) {
//            try {
//                updateShipmentsService.execute(new UpdateShipmentRunnable(queue.take(), shipmentService, terminalService));
//                i++;
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }

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

        System.out.println(" shipment evals: " +shipmentService.getShipmentEvals().size());
        System.out.println(" customer evals: " +customerService.getCustomerEval().size());
        System.out.println(" checkpoint evals: " +checkpointService.getCheckpointEvals().size());
        System.out.println(" parcel evals: " +parcelService.getParcelEvals().size());

        evals.addAll(shipmentService.getShipmentEvals());
        evals.addAll(parcelService.getParcelEvals());
        evals.addAll(customerService.getCustomerEval());
        evals.addAll(checkpointService.getCheckpointEvals());

        System.out.println("shipments in customer local: " + sender.getShipments().size());
        System.out.println("shipments in customer local: " + customerService.findByID(sender.getCustomerID()).get().getShipments().size());

        System.out.println("CHECKPOINTS: " + shipmentService.findByID(1L).getParcels().get(0).getCheckpoints());
        System.out.println("cp in parcel: " + shipmentService.findByID(1L).getParcels().size());


        System.out.println("queue size: " + queue.size());
        System.out.println("shipments: " + shipmentService.count());
        System.out.println("parcels: " + parcelService.count());
        System.out.println("checkpoints: " + checkpointService.count());

        System.out.println("Number of shipments in terminal 14: " + terminalService.returnTerminalFromZip("6008").getShipmentNumber());
        System.out.println("Number of checkpoints in terminal 14: " + terminalService.returnTerminalFromZip("0021").getCheckpointNumber());
        System.out.println("checkpoints in parcel from db: " + shipmentService.findByID(1L).getParcels().get(0).getCheckpoints().size());


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









