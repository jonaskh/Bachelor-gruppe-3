//package no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.JOOQTests;
//
//import JOOQ.repositories.CustomerRepository;
//import JOOQ.repositories.ShipmentRepository;
//import JOOQ.repositories.TerminalIdRepository;
//import JOOQ.repositories.ValidPostalCodesRepository;
//import JOOQ.service.CheckpointService;
//import JOOQ.service.CustomerService;
//import JOOQ.service.ParcelService;
//import JOOQ.service.ShipmentService;
//import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.CheckpointDao;
//import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.CustomerDao;
//import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.ParcelDao;
//import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.ShipmentDao;
//import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Customer;
//import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Shipment;
//import org.jooq.DSLContext;
//import org.jooq.SQLDialect;
//import org.jooq.impl.DSL;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//public class newTest {
//    private DSLContext dslContext;
//    private CustomerRepository customerRepository;
//    private ValidPostalCodesRepository validPostalCodesRepository;
//
//    @BeforeEach
//    void setUp() throws SQLException {
//        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");
//        dslContext = DSL.using(conn, SQLDialect.POSTGRES);
//        customerRepository = new CustomerRepository(dslContext);
//        validPostalCodesRepository = new ValidPostalCodesRepository(dslContext);
//    }
//
//    @Test
//    public void testMultipleShipments() {
//        String postalCode1 = validPostalCodesRepository.getRandomZipCode();
//        String postalCode2 = validPostalCodesRepository.getRandomZipCode();
//        CustomerService customerService = new CustomerService(new CustomerDao(dslContext.configuration()));
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime expectedDeliveryDate = now.plusDays(7);
//        ShipmentService shipmentService = new ShipmentService(new ShipmentDao(dslContext.configuration()));
//        TerminalIdRepository terminalIdRepository = new TerminalIdRepository(dslContext);
//        ShipmentRepository shipmentRepository = new ShipmentRepository(dslContext);
//        List<Long> timeTakenList = new ArrayList<>(); // Create list to store the time taken
//
//        //Create two customers, sender and receiver
//        Customer sender = new Customer()
//                .setCustomerId(customerRepository.getNextCustomerId())
//                .setAddress("Engveien 23")
//                .setName("Stian")
//                .setZipCode(postalCode1);
//        Customer savedSender = customerService.create(sender);
//
//        Customer receiver = new Customer()
//                .setCustomerId(customerRepository.getNextCustomerId())
//                .setAddress("Frode Rinnans Vei 32")
//                .setName("Daniel")
//                .setZipCode(postalCode2);
//        Customer savedReceiver = customerService.create(receiver);
//
//        //Create 100 shipments with the created
//
//        //Create 100 shipments with the created sender and receiver
//        for (int i = 0; i < 100; i++) {
//            long shipmentCreatedTime = System.nanoTime(); // get current time before creating shipment in nanoseconds
//            Shipment shipment = new Shipment()
//                    .setDelivered(false)
//                    .setPayerId(savedSender.getCustomerId())
//                    .setReceiverId(savedReceiver.getCustomerId())
//                    .setSenderId(savedSender.getCustomerId())
//                    .setTimeCreated(LocalDateTime.now()) // set time shipment was created
//                    .setExpectedDeliveryDate(expectedDeliveryDate);
//            Shipment savedShipment = shipmentService.create(shipment);
//            System.out.println(savedShipment.toString());
//
//            long shipmentSavedTime = System.nanoTime(); // get time after shipment is saved in nanoseconds
//            long timeTaken = shipmentSavedTime - shipmentCreatedTime; // calculate time taken
//            timeTakenList.add(timeTaken); // add to list
//        }
//
//        // Calculate average time taken
//        long total = 0;
//        for (Long timeTaken : timeTakenList) {
//            total += timeTaken;
//        }
//        double averageTimeTaken = (double) total / timeTakenList.size();
//        System.out.println("Average time taken: " + averageTimeTaken + " ns");
//
//        // Calculate highest and lowest times taken
//        long highestTimeTaken = Long.MIN_VALUE;
//        long lowestTimeTaken = Long.MAX_VALUE;
//        for (Long timeTaken : timeTakenList) {
//            if (timeTaken > highestTimeTaken) {
//                highestTimeTaken = timeTaken;
//            }
//            if (timeTaken < lowestTimeTaken) {
//                lowestTimeTaken = timeTaken;
//            }
//        }
//        System.out.println("Shipment that took the most time amount of time to create: " + highestTimeTaken + " ns");
//        System.out.println("Shipment that took the least amount of time to create: " + lowestTimeTaken + " ns");
//    }
//}
//
//
//
//
//
