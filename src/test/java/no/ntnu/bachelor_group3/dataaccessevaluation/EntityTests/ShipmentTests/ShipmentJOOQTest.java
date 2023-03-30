package no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.ShipmentTests;

import JOOQ.repositories.ShipmentRepository;
import JOOQ.service.*;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.CustomerDao;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.ShipmentDao;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Customer;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Shipment;
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

public class ShipmentJOOQTest {
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
        Customer customer = new Customer()
                .setAddress("Oslo")
                .setName("Stian")
                .setZipCode("2008");
        CustomerService service = new CustomerServiceImpl(new CustomerDao(dslContext.configuration()));

        Customer savedCustomer = service.create(customer);
        Shipment shipment = new Shipment()
                .setDelivered(false)
                .setCustomerId(savedCustomer.getCustomerid())
                .setPayerId(savedCustomer.getCustomerid())
                .setReceivingZip("7050")
                .setReceivingAddress("Trondheim")
                .setReceiverName("Daniel")
                .setSenderId(savedCustomer.getCustomerid());
        ShipmentService shipmentService = new ShipmentServiceImpl(new ShipmentDao(dslContext.configuration()));
        Shipment savedShipment = shipmentService.create(shipment);


        assertNotNull(savedShipment.getOrderId());
    }

}
