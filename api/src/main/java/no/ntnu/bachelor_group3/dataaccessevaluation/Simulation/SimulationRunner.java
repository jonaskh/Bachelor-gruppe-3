package no.ntnu.bachelor_group3.dataaccessevaluation.Simulation;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Customer;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.dataaccessevaluation.Runnables.*;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.*;

public class SimulationRunner {

    private final int SHIPMENTS = 10000;
    private final int THREADS = 1;
    private final ExecutorService addShipmentsExecutor = Executors.newFixedThreadPool(THREADS);
    private final ScheduledExecutorService findLocationofShipmentExecutor = Executors.newScheduledThreadPool(THREADS);
    private final ScheduledExecutorService updateShipmentExecutor = Executors.newScheduledThreadPool(2);
    private final ScheduledExecutorService updateCustomerExecutor = Executors.newScheduledThreadPool(THREADS);
    private final ScheduledExecutorService shipmentCountExecutor = Executors.newScheduledThreadPool(THREADS);


    private final ShipmentService shipmentService;
    private final CustomerService customerService;
    private final ParcelService parcelService;
    private final CheckpointService checkpointService;
    private static List<String> evals = new ArrayList<>();

//    private ArrayBlockingQueue<Shipment> shipmentQueue = new ArrayBlockingQueue<>(100000);


    private final Random random = new Random();

    public SimulationRunner(ShipmentService shipmentService, CustomerService customerService, TerminalService terminalService, ParcelService parcelService, CheckpointService checkpointService) {
        this.shipmentService = shipmentService;
        this.customerService = customerService;
        this.parcelService = parcelService;
        this.checkpointService = checkpointService;
    }

    public void simulate() {
        System.out.println("Starting simulation...");

        Customer sender = new Customer("Ålesund", "Jonas", "6008");
        Customer receiver = new Customer("Oslo", "Tarjei", "0021");

        customerService.save(sender);
        customerService.save(receiver);


        //adds a number of shipments to the database, and also to
        //the queue of shipments that the updates collect from
        for (int i = 0; i < SHIPMENTS; i++) {
            Shipment ship = new Shipment(sender, sender, receiver);
            addShipmentsExecutor.execute(new UpdateShipmentRunnable(ship, shipmentService));
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //run find shipment location after a 0.5 second delay every second to simulate higher load.
        for (int k = 0; k < 50; k++) {
            findLocationofShipmentExecutor.scheduleAtFixedRate(new FindShipmentRunnable(
                    shipmentService, shipmentService.findByID(1L)), 1000, 2000, TimeUnit.MILLISECONDS);
        }

        //returns number of shipments at a given time
        for (int j = 0; j < 10; j++) {
            shipmentCountExecutor.scheduleAtFixedRate(shipmentService::count,
                    1000, 5000, TimeUnit.MILLISECONDS);
        }
        //further methods and fields excluded





            try {
                //stops the executors from accepting new tasks
                addShipmentsExecutor.shutdown();
                updateCustomerExecutor.shutdown();
                updateShipmentExecutor.shutdown();
                findLocationofShipmentExecutor.shutdown();
                shipmentCountExecutor.shutdown();


                //stops the thread pools if no more tasks, an exception occurs or timeout.

                updateShipmentExecutor.awaitTermination(2, TimeUnit.MINUTES);
                addShipmentsExecutor.awaitTermination(2, TimeUnit.MINUTES);

                findLocationofShipmentExecutor.awaitTermination(2, TimeUnit.MINUTES);
                shipmentCountExecutor.awaitTermination(2, TimeUnit.MINUTES);

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

            System.out.println("total evals: " + evals.size());
        }
    }












