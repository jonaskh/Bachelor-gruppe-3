//package no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.JOOQTests;
//
//
//import JOOQ.repositories.CustomerRepository;
//import JOOQ.repositories.TerminalIdRepository;
//import JOOQ.service.CustomerService;
//import com.github.javafaker.Faker;
//import JOOQ.extendedPojos.CustomCustomerPojo;
//import org.jooq.DSLContext;
//import org.jooq.SQLDialect;
//import org.jooq.impl.DSL;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class CustomCustomerJOOQTest {
//
//
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
//    public void testFindAll() {
//        for (int i = 0; i < 10; i++) {
//            CustomCustomerPojo customer = new CustomCustomerPojo(dslContext);
//
//            System.out.println(customer);
//
//        }
//    }
//    }
