//package no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.JOOQTests;
//
//import JOOQ.repositories.ShipmentRepository;
//import JOOQ.service.ShipmentService;
//import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.CustomerDao;
//import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.ShipmentDao;
//import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Customer;
//import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Shipment;
//import org.jooq.DSLContext;
//import org.jooq.SQLDialect;
//import org.jooq.impl.DSL;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class ShipmentJOOQTest {
//    private DSLContext dslContext;
//
//
//    @BeforeEach
//    void setUp() throws SQLException {
//        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");
//        dslContext = DSL.using(conn, SQLDialect.POSTGRES);
//    }
//
//    @Test
//    public void testFindAllShipments() {
//        ShipmentRepository repository = new ShipmentRepository(dslContext);
//        List<Shipment> shipments = repository.findAll();
//        assertNotNull(shipments);
//        assertFalse(shipments.isEmpty());
//    }
//
//    @Test
//    public void testCreateShipment() {
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime expectedDeliveryDate = LocalDateTime.now().plusDays(7);
//
//
//
//        Shipment shipment = new Shipment()
//                .setDelivered(false)
//                .setPayerId(5L)
//                .setReceiverId(6L)
//                .setSenderId(6L)
//                .setTimeCreated(now)
//                .setExpectedDeliveryDate(expectedDeliveryDate);
//        ShipmentService shipmentService = new ShipmentService(new ShipmentDao(dslContext.configuration()));
//        Shipment savedShipment = shipmentService.create(shipment);
//
//
//        assertNotNull(savedShipment.getShipmentId());
//    }
//
//}
