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



    private ExecutorService executor1 = Executors.newFixedThreadPool(5);
    private ExecutorService updateShipmentsService = Executors.newFixedThreadPool(5);
    private ScheduledExecutorService findShipmentInCustomerService = Executors.newScheduledThreadPool(5);
    private ScheduledExecutorService findShipmentsInTerminalService = Executors.newScheduledThreadPool(5);


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
        System.out.println("Starting simulation...");

        Customer sender = new Customer("Ålesund", "Jonas", "6008");
        Customer receiver = new Customer("Oslo", "Tarjei", "0021");

        customerService.save(sender);
        customerService.save(receiver);



        //prints the last location of a shipment
        for (int i = 0; i < 100; i++) {
            executor1.execute(new UpdateShipmentRunnable(new Shipment(sender, sender, receiver), shipmentService, terminalService));
        }

        //run find shipment location after a 0.5 second delay every second to simulate higher load.
        for (int k = 0; k < 10; k++) {
            findShipmentInCustomerService.scheduleAtFixedRate(new FindShipmentRunnable(shipmentService, shipmentService.findByID(1L)), 500, 500, TimeUnit.MILLISECONDS);
        }

        //run find shipments in terminal every second to simulate higher load.
        for (int j = 0; j < 10; j++) {
            findShipmentsInTerminalService.scheduleAtFixedRate(new TerminalShipmentsRunnable(shipmentService, terminalService, terminalService.returnTerminalFromZip("6300")), 500, 10000, TimeUnit.MILLISECONDS);
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

        //ensure number of evals is correct
        System.out.println(" shipment evals: " +shipmentService.getShipmentEvals().size());
        System.out.println(" customer evals: " +customerService.getCustomerEval().size());
        System.out.println(" checkpoint evals: " +checkpointService.getCheckpointEvals().size());
        System.out.println(" parcel evals: " +parcelService.getParcelEvals().size());

        //adds all evals to one list
        evals.addAll(shipmentService.getShipmentEvals());
        evals.addAll(parcelService.getParcelEvals());
        evals.addAll(customerService.getCustomerEval());
        evals.addAll(checkpointService.getCheckpointEvals());

        //removes null values, quite time expensive so move it to frontend
//        evals.removeIf(n -> (n.startsWith("0"))); //removes all values that is zero, meaning they are cached and not retrieved from database

        System.out.println("total evals: " + evals.size());

        System.out.println("shipments in customer local: " + sender.getShipments().size());
        System.out.println("shipments in customer local: " + customerService.findByID(sender.getCustomerID()).get().getShipments().size());

        System.out.println("Number of shipments in terminal 14: " + terminalService.returnTerminalFromZip("6008").getShipmentNumber());
    }
}










