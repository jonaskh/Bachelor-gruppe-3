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
//import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.enums.CheckpointType;
//import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.ParcelDao;
//import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.*;
//import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.CheckpointDao;
//import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.CustomerDao;
//import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.ShipmentDao;
//import org.glassfish.jaxb.core.v2.TODO;
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
//    public void OneLifeCycleShipmentTest() {
//        String postalCode1 = validPostalCodesRepository.getRandomZipCode();
//        String postalCode2 = validPostalCodesRepository.getRandomZipCode();
//        CustomerService service = new CustomerService(new CustomerDao(dslContext.configuration()));
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime expectedDeliveryDate = LocalDateTime.now().plusDays(7);
//        ShipmentService shipmentService = new ShipmentService(new ShipmentDao(dslContext.configuration()));
//        CheckpointService checkpointService = new CheckpointService(new CheckpointDao(dslContext.configuration()));
//        ParcelService parcelService = new ParcelService(new ParcelDao(dslContext.configuration()));
//        TerminalIdRepository terminalIdRepository = new TerminalIdRepository(dslContext);
//        ShipmentRepository shipmentRepository = new ShipmentRepository(dslContext);
//
//
//
//
//
//
//        //TerminalId terminalId = dslContext.selectFrom(TERMINAL_ID).where(TERMINAL_ID.POSTAL_CODE.eq(postalCode)).fetchOneInto(TerminalId.class);
//        //assertNotNull(terminalId);
//
//        //Create two customers, sender and receiver
//        Customer sender = new Customer()
//                .setCustomerId(customerRepository.getNextCustomerId())
//                .setAddress("Engveien 23")
//                .setName("Stian")
//                .setZipCode(postalCode1);
//        Customer savedSender = service.create(sender);
//
//        Customer receiver = new Customer()
//                .setCustomerId(customerRepository.getNextCustomerId())
//                .setAddress("Frode Rinnans Vei 32")
//                .setName("Daniel")
//                .setZipCode(postalCode2);
//        Customer savedReceiver = service.create(receiver);
//
//        //Create one shipment with the created sender and receiver
//        Shipment shipment = new Shipment()
//                .setDelivered(false)
//                .setPayerId(savedSender.getCustomerId())
//                .setReceiverId(savedReceiver.getCustomerId())
//                .setSenderId(savedSender.getCustomerId())
//                .setTimeCreated(now)
//                .setExpectedDeliveryDate(expectedDeliveryDate);
//        Shipment savedShipment = shipmentService.create(shipment);
//        System.out.println(savedShipment.toString());
//
//
//        Parcel parcel = new Parcel()
//                .setWeight(200.0)
//                .setWeightClass(1)
//                .setShipmentId(savedShipment.getShipmentId());
//        Parcel savedParcel = parcelService.create(parcel);
//        assertNotNull(savedParcel.getParcelId());
//        System.out.println(savedParcel.toString());
//
//
//        //TODO: Må finne en måte å gjøre slik at vi kan sette TerminalType til den String versjonen av Enumet slik at vi får mer kontroll.
//
//        //Creates a basic checkpoint with terminal for when it is collected, maybe a customers address is needed in this table aswell?
//        Checkpoint checkpoint = new Checkpoint()
//                .setCost(100.0)
//                .setTime(now)
//                .setParcelId(savedParcel.getParcelId());
//        checkpoint.setType((short) 0);
//        Checkpoint savedCheckpoint = checkpointService.create(checkpoint);
//        System.out.println(savedCheckpoint.toString()+"Main Checkpoint");
//
//        //Creates a checkpoint with the terminal id connected to the senders zipcode - recieved at first terminal.
//        String senderPostalCode = savedSender.getZipCode();
//        Optional<TerminalId> senderTerminalId = terminalIdRepository.findByPostalCode(senderPostalCode);
//        System.out.println(senderPostalCode);
//        System.out.println(savedSender.toString());
//            Checkpoint checkpoint1 = new Checkpoint()
//                    .setCost(100.0)
//                    .setTime(now)
//                    .setTerminalId(senderTerminalId.get().getTerminalIdTerminalId())
//                    .setParcelId(savedParcel.getParcelId());
//            checkpoint1.setType((short) 1);
//            Checkpoint savedCheckpoint1 = checkpointService.create(checkpoint1);
//            System.out.println(savedCheckpoint1.getTerminalId());
//            System.out.println(savedCheckpoint1.toString()+"First Checkpoint");
//
//
//        //Creates a checkpoint for when the package is loaded for transport. Does not change terminal id.
//        Checkpoint checkpoint2 = new Checkpoint()
//                .setCost(100.0)
//                .setTime(now)
//                .setParcelId(savedParcel.getParcelId())
//                .setTerminalId(checkpoint1.getTerminalId());
//        checkpoint2.setType((short) 2);
//        Checkpoint savedCheckpoint2 = checkpointService.create(checkpoint2);
//        System.out.println(savedCheckpoint2.toString()+"Second Checkpoint");
//
//        //Creates a checkpoint for when the package is received at final terminal. Does change the terminal_id.
//        String receiverPostalCode = savedReceiver.getZipCode();
//        Optional<TerminalId> receiverTerminalId = terminalIdRepository.findByPostalCode(receiverPostalCode);
//        Checkpoint checkpoint3 = new Checkpoint()
//                .setCost(100.0)
//                .setTime(now)
//                .setTerminalId(receiverTerminalId.get().getTerminalIdTerminalId())
//                .setParcelId(savedParcel.getParcelId());
//        checkpoint3.setType((short) 3);
//        Checkpoint savedCheckpoint3 = checkpointService.create(checkpoint3);
//        System.out.println(savedCheckpoint3.toString()+"Third Checkpoint");
//
//
//    }
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
