package no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.Lifecycle;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Customer;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Parcel;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.TestConfiguration;
import no.ntnu.bachelor_group3.dataaccessevaluation.Runnables.CustomerRunnable;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.*;
import no.ntnu.bachelor_group3.dataaccessevaluation.Runnables.ShipmentRunnable;
import no.ntnu.bachelor_group3.dataaccessevaluation.Simulation.SimulationRunner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest(showSql = false)
@Import(TestConfiguration.class) //to load the required beans for the services
public class MultiThreadTests {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ShipmentService shipmentService;

    @Autowired
    private TerminalService terminalService;

    @Autowired
    private ValidPostalCodeService validPostalCodeService;

    @Autowired
    private CheckpointService checkpointService;

    private SimulationRunner simulationRunner = new SimulationRunner();

    @Autowired
    private ParcelService parcelService;


    @Test
    @DisplayName("Ensure the runnables work as expected in the thread pool")
    public void concurrencyTest() {
        validPostalCodeService.ReadCSVFile();
        Customer sender = new Customer("Oslo", "Tarjei", "0021");
        Customer receiver = new Customer("Ålesund", "Jonas", "6008");
        customerService.add(sender);
        customerService.add(receiver);


        for (int i = 0; i < 30; i++) {
            Shipment shipment = new Shipment(sender, sender, receiver);
            simulationRunner.work(shipment, shipmentService);
        }
        simulationRunner.getExecutor().shutdown();
        try {
            simulationRunner.getExecutor().awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

//        System.out.println(shipmentService.count());
    }

    @Test
    @DisplayName("Adding multiple shipments and their corresponding entities to database concurrently")
    public void concurrentShipmentAddTest() {
        List<Shipment> shipments = new ArrayList<>();


        for (int i = 0; i < 500; i++) {
            Customer customer = new Customer();
            Customer customer2 = new Customer();

            Shipment shipment = new Shipment(customer, customer, customer2);
            shipments.add(shipment);

        }
        ExecutorService executor = Executors.newFixedThreadPool(5); //5 threads

        var timeTakenToAdd500 = System.currentTimeMillis();
        for (Shipment shipment : shipments) {
            ShipmentRunnable sr = new ShipmentRunnable(shipment, shipmentService);
            var future = executor.submit(() -> {
                shipmentService.concurrentAdd(shipment);
            });
        }

        executor.shutdown(); //shutdown the service, it will not take any more tasks but finish existing ones

        try {
            executor.awaitTermination(2, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for (Shipment shipment: shipments) {
            shipmentService.printParcelsFromDB(shipment);
        }
        var timeTaken = System.currentTimeMillis() - timeTakenToAdd500;

        System.out.println("Time taken to add 500 shipments: " + timeTaken + "ms");

        System.out.println("shipment count: " + shipmentService.count());
        System.out.println("customer count: " + customerService.count());
        System.out.println("parcel count: " + parcelService.count());
    }


    @Test
    @DisplayName("Assert 20 customers are added to the database concurrently")
    public void concurrentAddCustomerTest() {
        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 20; i++) {
            CustomerRunnable customerRunnable = new CustomerRunnable(new Customer(), customerService);
            executor.submit(customerRunnable);
        }
        assertEquals("Number of customers", 20, customerService.count());
    }

    @Test
    public void concurrentParcelAddTest() {
        Customer customer = new Customer();
        Shipment shipment = new Shipment(customer, customer, customer);
        shipmentService.add(shipment);

        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 10; i++) {
            Parcel parcel = new Parcel(shipment, 10.1f);
            executor.submit(() -> {
                parcelService.save(parcel);
            });
        }

        executor.shutdown();

        try {
            executor.awaitTermination(2, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Parcel count: " + parcelService.count());
    }


    @Test
    public void addCustomerFromQueueTest() {
        ExecutorService executor = Executors.newFixedThreadPool(5);

        BlockingQueue<Shipment> customerQueue = new ArrayBlockingQueue<>(100);
        List<Shipment> shipments = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            Customer customer = new Customer();
            shipments.add(new Shipment(customer, customer, customer));
        }
        customerQueue.addAll(shipments);

        for (int k = 0; k < shipments.size(); k++) {
            executor.execute(() -> {
                Shipment shipment = customerQueue.poll();
                shipmentService.add(shipment);
            });
        }
        System.out.println("Count: " + customerService.count());

        executor.shutdown();
        try {
            executor.awaitTermination(1,TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void addMultipleShipmentsWithSameCustomer() {

        for (int i = 0; i < 10; i++) {
            Customer customer = new Customer();
            Customer customer2 = new Customer();
            Shipment shipment = new Shipment(customer, customer, customer2);

            shipmentService.add(shipment);
            shipmentService.printShipmentInfo(shipment);
        }
        System.out.println("Shipment count: " + shipmentService.count());
        System.out.println("Customer count: " + customerService.count());
        System.out.println("Parcel count: " + parcelService.count());
    }
}

