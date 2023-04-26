//package no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.Lifecycle;
//
//import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Customer;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Parcel;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
//import no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.TestConfiguration;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Runnables.CustomerRunnable;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Runnables.ShipmentCallable;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Services.*;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Runnables.ShipmentRunnable;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Simulation.SimulationRunner;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.*;
//
//import static org.springframework.test.util.AssertionErrors.assertEquals;
//import static org.springframework.test.util.AssertionErrors.assertNotEquals;
//
//@ExtendWith(SpringExtension.class)
//@DataJpaTest(showSql = false)
//@Import(TestConfiguration.class) //to load the required beans for the services
//public class MultiThreadTests {
//
//    @Autowired
//    private CustomerService customerService;
//
//    @Autowired
//    private ShipmentService shipmentService;
//
//    @Autowired
//    private TerminalService terminalService;
//
//    @Autowired
//    private ValidPostalCodeService validPostalCodeService;
//
//    @Autowired
//    private CheckpointService checkpointService;
//
//    private SimulationRunner simulationRunner = new SimulationRunner();
//
//    @Autowired
//    private ParcelService parcelService;
//
//    private static List<String> timestamps = new ArrayList<>();
//
//    private static List<Future<String>> futureList = new ArrayList<>();
//
//    private static List<String> evals = new CopyOnWriteArrayList<>();
//
//
//
//    @Test
//    @DisplayName("Adding multiple shipments and their corresponding entities to database concurrently")
//    public void concurrentShipmentRunnableAddTest() {
//        validPostalCodeService.ReadCSVFile();
//        List<Shipment> shipments = new ArrayList<>();
//
//        for (int i = 0; i < 500; i++) {
//            Customer customer = new Customer();
//            Customer customer2 = new Customer();
//
//            Shipment shipment = new Shipment(customer, customer, customer2);
//            shipments.add(shipment);
//        }
//        ExecutorService executor = Executors.newScheduledThreadPool(5); //5 threads
//
//        for (Shipment shipment : shipments) {
//            ShipmentRunnable sr = new ShipmentRunnable(shipment, shipmentService);
//            executor.execute(sr);
//        }
//        executor.shutdown(); //shutdown the service, it will not take any more tasks but finish existing ones
//
//        try {
//            executor.awaitTermination(2, TimeUnit.MINUTES);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//
//
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//
//
//        evals.addAll(shipmentService.getShipmentEvals());
//        evals.addAll(parcelService.getParcelEvals());
//        evals.addAll(customerService.getCustomerEval());
//        evals.addAll(checkpointService.getCheckpointEvals());
//
//        evals.forEach(System.out::println);
//
//        System.out.println("\n \n shipment count: " + shipmentService.count());
//        System.out.println("customer count: " + customerService.count());
//        System.out.println("parcel count: " + parcelService.count());
//        System.out.println("checkpoint count: " + checkpointService.count());
//        System.out.println("terminal count: " + terminalService.count());
//
//        System.out.println(shipments.get(0).getShipment_id() + " has " + shipments.get(0).getParcels().size());
//        System.out.println(shipmentService.findByID(1L).getParcels().size());
//
//
//
//
//        System.out.println("Number of evaluations: " + evals.size());
//
//    }
//
//    @Test
//    @DisplayName("Print timer for a positive and a negative findByID call to see difference in time")
//    public void testFindByIDTimer() {
//        Customer customer = new Customer();
//        Shipment shipment = new Shipment(customer, customer, customer);
//        shipmentService.cascadingAdd(shipment);
//        System.out.println("Positive find: " + shipmentService.findByID(1L));
//
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println("Negative find: " + shipmentService.findByID(2L));
//
//    }
//
//
//
//    @Test
//    public void testCallableShipmentAdd() {
//        Customer customer5 = new Customer();
//        Shipment test = new Shipment(customer5, customer5, customer5);
//        ExecutorService executor = Executors.newFixedThreadPool(5); //5 threads
//
//        for (int i = 0; i < 500; i++) {
//            Customer customer = new Customer();
//            Customer customer2 = new Customer();
//            Shipment shipment = new Shipment(customer, customer, customer2);
//
//            Future<String> future = executor.submit(new ShipmentCallable(shipment, shipmentService));
//            futureList.add(future);
//        }
//
//
//        executor.shutdown();
//
//        try {
//            executor.awaitTermination(1, TimeUnit.MINUTES);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        List<String> resultList = new ArrayList<>();
//        for (Future<String> future : futureList) {
//            String result = null;
//            try {
//                result = future.get();
//            } catch (InterruptedException | ExecutionException e) {
//                throw new RuntimeException(e);
//            }
//            resultList.add(result);
//        }
//        resultList.forEach(System.out::println);
//
//        System.out.println("shipment count: " + shipmentService.count());
//        System.out.println("customer count: " + customerService.count());
//        System.out.println("parcel count: " + parcelService.count());
//        assertEquals("Number of items in future", 500, futureList.size());
//    }
//
//
//    @Test
//    @DisplayName("Assert 500 customers are added to the database concurrently")
//    public void concurrentAddCustomerTest() {
//        ExecutorService executor = Executors.newFixedThreadPool(5);
//        for (int i = 0; i < 500; i++) {
//            Customer customer = new Customer();
//            Future<String> future = executor.submit(new CustomerRunnable(customer, customerService));
//            futureList.add(future);
//        }
//
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println(customerService.count());
//
//        List<String> resultList = new ArrayList<>();
//        for (Future<String> future : futureList) {
//            String result = null;
//            try {
//                result = future.get();
//            } catch (InterruptedException | ExecutionException e) {
//                throw new RuntimeException(e);
//            }
//            resultList.add(result);
//        }
//        resultList.forEach(System.out::println);
//        }
//    }
//
//    //TODO: Add neggative tests to ensure cannot input duplicate entities
//
//
//
