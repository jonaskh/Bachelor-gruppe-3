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
import java.util.Map;
import java.util.concurrent.*;

public class SimpleSimulationRunner {

    private ShipmentService shipmentService;
    private CustomerService customerService;
    private TerminalService terminalService;
    private ValidPostalCodeService validPostalCodeService;
    private ParcelService parcelService;
    private CheckpointService checkpointService;
    private static List<String> evals = new CopyOnWriteArrayList<>();


    int threads = 10;
    int loadSize = 50000;


    private ExecutorService executor1 = Executors.newFixedThreadPool(threads);
    private ExecutorService executor2 = Executors.newFixedThreadPool(threads);



    private final ArrayBlockingQueue<Shipment> queue = new ArrayBlockingQueue<>(loadSize + 1000);


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

        for (int i = 0; i < loadSize; i++) {
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
            executor1.shutdown();
            //stops the thread pools if no more tasks, an exception occurs or timeout.
            System.out.println("Update shipments done");
            executor1.awaitTermination(2, TimeUnit.MINUTES);
            System.out.println("Adding done");


        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Error in await termination");
        }
        for (int i = 0; i < loadSize; i++) {
            Shipment shipment = queue.poll(); // assuming the queue still holds the shipments
            FindShipmentRunnable findShipmentsRunnable = new FindShipmentRunnable(shipmentService, customerService, shipment); // You need to define it according to your actual usage
            executor2.execute(findShipmentsRunnable);
        }

        try {
            executor2.shutdown();
            executor2.awaitTermination(2, TimeUnit.MINUTES);
            System.out.println("Finding done");
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Error in await termination");
        }

        List<Long> timeTakenToCreateList = shipmentService.getTimeTakenToCreateList();
        Map<String, Long> createStatistics = shipmentService.calculateStatistics(timeTakenToCreateList);

// Print the create statistics
        System.out.println("Create Statistics:");
        System.out.println("Min: " + createStatistics.get("min"));
        System.out.println("Max: " + createStatistics.get("max"));
        System.out.println("Standard Deviation: " + createStatistics.get("standardDeviation"));
        System.out.println("Average: " + createStatistics.get("average"));


        List<Long> timeTakenToReadList = shipmentService.getTimeTakenToReadList();
        Map<String, Long> readStatistics = shipmentService.calculateStatistics(timeTakenToReadList);

// Print the read statistics
        System.out.println("Read Statistics:");
        System.out.println("Min: " + readStatistics.get("min"));
        System.out.println("Max: " + readStatistics.get("max"));
        System.out.println("Standard Deviation: " + readStatistics.get("standardDeviation"));
        System.out.println("Average: " + readStatistics.get("average"));



    }
}
