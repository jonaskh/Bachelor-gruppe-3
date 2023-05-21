//package no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.JOOQTests;
//
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
//import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.*;
//import org.jooq.DSLContext;
//import org.jooq.SQLDialect;
//import org.jooq.impl.DSL;
//import org.jooq.meta.derby.sys.Sys;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//public class LifecycleJOOQTest {
//    private DSLContext dslContext;
//    private CustomerRepository customerRepository;
//    private ValidPostalCodesRepository validPostalCodesRepository;
//    private List<Checkpoint> checkpoints;
//
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
//        CheckpointService checkpointService = new CheckpointService(new CheckpointDao(dslContext.configuration()));
//        ParcelService parcelService = new ParcelService(new ParcelDao(dslContext.configuration()));
//        TerminalIdRepository terminalIdRepository = new TerminalIdRepository(dslContext);
//        ShipmentRepository shipmentRepository = new ShipmentRepository(dslContext);
//        ParcelDao parcelDao = new ParcelDao(dslContext.configuration());
//        checkpoints = new ArrayList<>();
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
//
//
//
//
//
//
////        Parcel parcel = new Parcel()
////                .setWeight(200.0)
////                .setWeightClass(1);
////        Parcel savedParcel = parcelService.create(parcel);
////        assertNotNull(savedParcel.getParcelId());
////        System.out.println(savedParcel.toString());
////
////
////
////
////        //Creates a basic checkpoint with terminal for when it is collected, maybe a customers address is needed in this table aswell?
////        Checkpoint checkpoint = new Checkpoint()
////                .setCost(100.0)
////                .setTime(now)
////                .setParcelId(savedParcel.getParcelId());
////        checkpoint.setType((short) 0);
////        Checkpoint savedCheckpoint = checkpointService.create(checkpoint);
////        System.out.println(savedCheckpoint.toString()+"Main Checkpoint");
////        checkpoints.add(checkpoint);
////
////
////        //Creates a checkpoint with the terminal id connected to the senders zipcode - recieved at first terminal.
////        String senderPostalCode = savedSender.getZipCode();
////        Optional<TerminalId> senderTerminalId = terminalIdRepository.findByPostalCode(senderPostalCode);
////        System.out.println(senderPostalCode);
////        System.out.println(savedSender.toString());
////            Checkpoint checkpoint1 = new Checkpoint()
////                    .setCost(100.0)
////                    .setTime(now)
////                    .setTerminalTerminalId(senderTerminalId.get().getTerminalIdTerminalId())
////                    .setParcelId(savedParcel.getParcelId());
////            checkpoint1.setType((short) 1);
////            Checkpoint savedCheckpoint1 = checkpointService.create(checkpoint1);
////        checkpoints.add(checkpoint1);
////
////
////        //Creates a checkpoint for when the package is loaded for transport. Does not change terminal id.
////        Checkpoint checkpoint2 = new Checkpoint()
////                .setCost(100.0)
////                .setTime(now)
////                .setParcelId(savedParcel.getParcelId())
////                .setTerminalTerminalId(checkpoint1.getTerminalTerminalId());
////        checkpoint2.setType((short) 2);
////        Checkpoint savedCheckpoint2 = checkpointService.create(checkpoint2);
////        checkpoints.add(checkpoint2);
////
////
////        //Creates a checkpoint for when the package is received at final terminal. Does change the terminal_id.
////        String receiverPostalCode = savedReceiver.getZipCode();
////        Optional<TerminalId> receiverTerminalId = terminalIdRepository.findByPostalCode(receiverPostalCode);
////        Checkpoint checkpoint3 = new Checkpoint()
////                .setCost(100.0)
////                .setTime(now)
////                .setTerminalTerminalId(receiverTerminalId.get().getTerminalIdTerminalId())
////                .setParcelId(savedParcel.getParcelId());
////        checkpoint3.setType((short) 3);
////        Checkpoint savedCheckpoint3 = checkpointService.create(checkpoint3);
////        checkpoints.add(checkpoint3);
////
////
////        System.out.println(checkpoints);
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//    }
//
//
//
//
//
//
//
//
//
//