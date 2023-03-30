package no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.CustomerTests;

import JOOQ.repositories.CustomerRepository;
import JOOQ.service.CustomerService;
import JOOQ.service.CustomerServiceImpl;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.CustomerDao;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Customer;
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

public class CustomerJOOQTest {
    private DSLContext dslContext;


    @BeforeEach
    void setUp() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");
        dslContext = DSL.using(conn, SQLDialect.POSTGRES);
    }

    @Test
    public void testFindAll() {
        CustomerRepository repository = new CustomerRepository(dslContext);
        List<Customer> Customers = repository.findAll();
        assertNotNull(Customers);
        assertFalse(Customers.isEmpty());
    }

    @Test
    public void testCreateCustomer() {
        Customer Customer = new Customer()
                .setAddress("Stavanger")
                .setName("Frank")
                .setZipCode("3009");
        CustomerService service = new CustomerServiceImpl(new CustomerDao(dslContext.configuration()));
        Customer savedCustomer = service.create(Customer);
        assertNotNull(savedCustomer.getCustomerid());
    }
}
