package no.ntnu.bachelor_group3.dataaccessevaluation.Simulation;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Customer;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.dataaccessevaluation.Runnables.AddShipmentsRunnable;
import no.ntnu.bachelor_group3.dataaccessevaluation.Runnables.FindShipmentRunnable;
import no.ntnu.bachelor_group3.dataaccessevaluation.Runnables.TerminalShipmentsRunnable;
import no.ntnu.bachelor_group3.dataaccessevaluation.Runnables.UpdateShipmentRunnable;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.*;

public class SimulationRunner {


    private final int SHIPMENTS = 10000;

    private final int THREADS = 10;
    private final ShipmentService shipmentService;
    private final CustomerService customerService;
    private final TerminalService terminalService;
    private final ParcelService parcelService;
    private final CheckpointService checkpointService;
    private static List<String> evals = new ArrayList<>();

    private ArrayBlockingQueue<Shipment> shipmentQueue = new ArrayBlockingQueue<>(SHIPMENTS);


    private final ExecutorService addShipmentsExecutor = Executors.newFixedThreadPool(THREADS);
    private final ScheduledExecutorService findLocationofShipmentExecutor = Executors.newScheduledThreadPool(THREADS);
    private final ScheduledExecutorService updateShipmentExecutor = Executors.newScheduledThreadPool(2);

    private final ScheduledExecutorService updateCustomerExecutor = Executors.newScheduledThreadPool(THREADS);


    public SimulationRunner(ShipmentService shipmentService, CustomerService customerService, TerminalService terminalService, ParcelService parcelService, CheckpointService checkpointService) {
        this.shipmentService = shipmentService;
        this.customerService = customerService;
        this.terminalService = terminalService;
        this.parcelService = parcelService;
        this.checkpointService = checkpointService;
    }

    Random random = new Random();

    public void simulate() {
        System.out.println("Starting simulation...");

        Customer sender = new Customer("Ålesund", "Jonas", "6008");
        Customer receiver = new Customer("Oslo", "Tarjei", "0021");

        customerService.save(sender);
        customerService.save(receiver);


        //prints the last location of a shipment
        for (int i = 0; i < SHIPMENTS; i++) {
            Shipment ship = new Shipment(sender, sender, receiver);
            addShipmentsExecutor.execute(new UpdateShipmentRunnable(ship, shipmentService, terminalService));
//            shipmentQueue.add(ship);
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


//        int i = 0;
//        var size = shipmentQueue.size();
//        System.out.println(size);
//        while (i < size) {
//            try {
//                updateShipmentExecutor.execute(new UpdateShipmentRunnable(shipmentQueue.take(), shipmentService, terminalService));
//                i++;
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }


//        to allow the other threads to let shipments be added before starting

        if (shipmentService.findByID(1L) != null) {
//            run find shipment location after a 0.5 second delay every second to simulate higher load.
            for (int k = 1; k < 20; k++) {

                findLocationofShipmentExecutor.scheduleAtFixedRate(new FindShipmentRunnable(shipmentService, shipmentService.findByID(1L)), 1000, 2000, TimeUnit.MILLISECONDS);
            }
        }



        //run find shipments in terminal every second to simulate higher load.
//        for (int j = 0; j < 5; j++) {
//            updateCustomerExecutor.scheduleAtFixedRate(new TerminalShipmentsRunnable(shipmentService, terminalService, terminalService.returnTerminalFromZip("6300")), 0, 500, TimeUnit.MILLISECONDS);
//        }


        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            //stops the executors from accepting new tasks

            addShipmentsExecutor.shutdown();

            updateCustomerExecutor.shutdown();
            findLocationofShipmentExecutor.shutdown();
            updateShipmentExecutor.shutdown();


            //stops the thread pools if no more tasks, an exception occurs or timeout.

            updateCustomerExecutor.awaitTermination(3, TimeUnit.MINUTES);
            addShipmentsExecutor.awaitTermination(3, TimeUnit.MINUTES);
            findLocationofShipmentExecutor.awaitTermination(3, TimeUnit.MINUTES);

            updateShipmentExecutor.awaitTermination(3, TimeUnit.MINUTES);


            System.out.println("Adding done");


        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Error in await termination");
        }

        //ensure number of evals is correct
        System.out.println(" shipment evals: " + shipmentService.getShipmentEvals().size());
        System.out.println(" customer evals: " + customerService.getCustomerEval().size());
        System.out.println(" checkpoint evals: " + checkpointService.getCheckpointEvals().size());
        System.out.println(" parcel evals: " + parcelService.getParcelEvals().size());

        //adds all evals to one list
        evals.addAll(shipmentService.getShipmentEvals());
        evals.addAll(parcelService.getParcelEvals());
        evals.addAll(customerService.getCustomerEval());
        evals.addAll(checkpointService.getCheckpointEvals());

        System.out.println("shipments: " + shipmentService.count());
        System.out.println("checkpoints: " + checkpointService.count());
        System.out.println("parcels: " + parcelService.count());

        //removes null values, quite time expensive so move it to frontend
//        evals.removeIf(n -> (n.startsWith("0"))); //removes all values that is zero, meaning they are cached and not retrieved from database

        System.out.println("total evals: " + evals.size());
    }
}











