package no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.JOOQTests;

import JOOQ.repositories.CustomerRepository;
import JOOQ.repositories.ValidPostalCodesRepository;
import JOOQ.service.CheckpointService;
import JOOQ.service.CustomerService;
import JOOQ.service.ShipmentService;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.enums.CheckpointType;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Checkpoint;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.CheckpointDao;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.CustomerDao;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.ShipmentDao;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Customer;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Shipment;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.TerminalId;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
public class LifecycleJOOQTest {
    private DSLContext dslContext;
    private CustomerRepository customerRepository;
    private ValidPostalCodesRepository validPostalCodesRepository;

    @BeforeEach
    void setUp() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");
        dslContext = DSL.using(conn, SQLDialect.POSTGRES);
        customerRepository = new CustomerRepository(dslContext);
        validPostalCodesRepository = new ValidPostalCodesRepository(dslContext);
    }

    @Test
    public void OneLifeCycleShipmentTest() {
        String postalCode1 = validPostalCodesRepository.getRandomZipCode();
        String postalCode2 = validPostalCodesRepository.getRandomZipCode();
        CustomerService service = new CustomerService(new CustomerDao(dslContext.configuration()));
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expectedDeliveryDate = LocalDateTime.now().plusDays(7);
        ShipmentService shipmentService = new ShipmentService(new ShipmentDao(dslContext.configuration()));
        CheckpointService checkpointService = new CheckpointService(new CheckpointDao(dslContext.configuration()));




        //TerminalId terminalId = dslContext.selectFrom(TERMINAL_ID).where(TERMINAL_ID.POSTAL_CODE.eq(postalCode)).fetchOneInto(TerminalId.class);
        //assertNotNull(terminalId);

        //Create two customers, sender and receiver
        Customer sender = new Customer()
                .setCustomerId(customerRepository.getNextCustomerId())
                .setAddress("Engveien 23")
                .setName("Stian")
                .setZipCode(postalCode1);
        Customer savedSender = service.create(sender);

        Customer receiver = new Customer()
                .setCustomerId(customerRepository.getNextCustomerId())
                .setAddress("Frode Rinnans Vei 32")
                .setName("Daniel")
                .setZipCode(postalCode2);
        Customer savedReceiver = service.create(receiver);

        //Create one shipment with the created sender and receiver
        Shipment shipment = new Shipment()
                .setDelivered(false)
                .setPayerId(savedSender.getCustomerId())
                .setReceiverId(savedReceiver.getCustomerId())
                .setSenderId(savedSender.getCustomerId())
                .setTimeCreated(now)
                .setExpectedDeliveryDate(expectedDeliveryDate);
        Shipment savedShipment = shipmentService.create(shipment);


        Checkpoint checkpoint = new Checkpoint()
                .setCost(100.0)
                .setTime(now)
                .setTerminalId(1);
        checkpoint.setType((short) 0);
        Checkpoint savedCheckpoint = checkpointService.create(checkpoint);
        System.out.println(savedCheckpoint.toString());




    }
    }


