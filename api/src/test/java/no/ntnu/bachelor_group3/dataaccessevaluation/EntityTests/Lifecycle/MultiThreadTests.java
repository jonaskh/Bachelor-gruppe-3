//package no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.Lifecycle;
//
//import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Customer;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
//
//import no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.TestConfiguration;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Runnables.AddShipmentsRunnable;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Services.*;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Runnables.UpdateShipmentRunnable;
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
//    @Test
//    @DisplayName("Adding multiple shipments and their corresponding entities to database concurrently")
//    public void concurrentShipmentRunnableAddTest() {
//        validPostalCodeService.ReadCSVFile();
//        List<Shipment> shipments = new ArrayList<>();
//        ExecutorService executor = Executors.newFixedThreadPool(10); //optimize number
//
//        for (int i = 0; i < 500; i++) {
//            Customer customer = new Customer("ålesund", "jonas", "6008");
//            Customer customer2 = new Customer("oslo", "tarjei", "0021");
//
//            Shipment shipment = new Shipment(customer, customer, customer2);
//            UpdateShipmentRunnable sr = new UpdateShipmentRunnable(shipment, shipmentService);
//            executor.execute(sr);
//        }
//
//        executor.shutdown(); //shutdown the service, it will not take any more tasks but finish existing ones
//
//        try {
//            executor.awaitTermination(2, TimeUnit.MINUTES);
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
//        System.out.println(shipmentService.findByID(1L).getParcels().size());
//
//
//        System.out.println("Number of evaluations: " + evals.size());
//    }
//
//
//    @Test
//    public void concurrentAddShipments() {
//        validPostalCodeService.createTerminals();
//
//        validPostalCodeService.ReadCSVFile();
//        ExecutorService executor = Executors.newFixedThreadPool(5);
//
////
////        customerService.save(customer);
////        customerService.save(customer2);
//
//        for (int i = 0; i < 500; i++) {
//
//            Customer customer = new Customer("Ålesund", "Jonas", "6008");
//            Customer customer2 = new Customer("Oslo", "Tarjei", "0021");
//
//
//            executor.execute(new AddShipmentsRunnable(new Shipment(customer, customer2, customer),shipmentService,customerService));
//        }
//
//
//        executor.shutdown(); //does not accept new tasks, but will finish existing ones
//
//        try {
//            executor.awaitTermination(1, TimeUnit.MINUTES); //terminates when all tasks are done, or 1 minute passes, whichever happens first
////            System.out.println(customerService.findByID(2L).get().getShipments());
//
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//
//
//    }
//
//    @Test
//    @DisplayName("Print timer for a positive and a negative findByID call to see difference in time")
//    public void testFindByIDTimer() {
//        Customer customer = new Customer();
//        Shipment shipment = new Shipment(customer, customer, customer);
//        shipmentService.addShipment(shipment);
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
//    private static class ExampleTask implements Runnable {
//
//        private String name;
//
//        public ExampleTask(String name) {
//            this.name = name;
//        }
//
//        @Override
//        public void run() {
//
//            try {
//                catchRun();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            // Do some work after the loop if necessary
//        }
//
//        public void catchRun() {
//            System.out.println("Working on thread: " + Thread.currentThread().getId() + "...");
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                System.out.println("F");
//                Thread.currentThread().interrupt();
//            }
//            System.out.println("Done working on thread: " + Thread.currentThread().getId());
//
//        }
//    }
//
//    @Test
//    public void scheduledExecutorTest() {
//        ScheduledExecutorService service = Executors.newScheduledThreadPool(5);
//        service.scheduleAtFixedRate(new ExampleTask("Task"), 0, 6, TimeUnit.SECONDS);
//        service.scheduleAtFixedRate(new ExampleTask("Task"), 0, 6, TimeUnit.SECONDS);
//        service.scheduleAtFixedRate(new ExampleTask("Task"), 0, 6, TimeUnit.SECONDS);
//        try {
//            service.awaitTermination(1, TimeUnit.MINUTES);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
//
//
//
//
