package no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.ShipmentTests;

import JOOQ.repositories.ShipmentRepository;
import JOOQ.repositories.TerminalRepository;
import JOOQ.service.ShipmentService;
import JOOQ.service.ShipmentServiceImpl;
import JOOQ.service.TerminalService;
import JOOQ.service.TerminalServiceImpl;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.ShipmentDao;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.TerminalDao;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Shipment;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Terminal;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ShipmentTestJOOQ {
    private DSLContext dslContext;


    @BeforeEach
    void setUp() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");
        dslContext = DSL.using(conn, SQLDialect.POSTGRES);
    }

    @Test
    public void testFindAllShipments() {
        ShipmentRepository repository = new ShipmentRepository(dslContext);
        List<Shipment> shipments = repository.findAll();
        assertNotNull(shipments);
        assertFalse(shipments.isEmpty());
    }

    @Test
    public void testCreateShipment() {
        Shipment shipment = new Shipment()
                .setCheckpointId(null)
                .setDelivered(false)
                .setCustomerId(0)
                .setPayerId(0)
                .setReceivingZip("7050")
                .setReceivingAddress("Trondheim")
                .setReceiverName("Daniel")
                .setSenderId(0);
        ShipmentService service = new ShipmentServiceImpl(new ShipmentDao(dslContext.configuration()));
        Shipment savedShipment = service.create(shipment);
        assertNotNull(savedShipment.getOrderId());
        assertEquals("Trondheim", savedShipment.getReceivingAddress());
    }

}
