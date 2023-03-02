package no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.CustomerTests;

import com.github.javafaker.Faker;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Customer;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.CustomerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateCustomerTest {
    Faker faker = new Faker(new Locale("nb-NO"));
    private final Logger logger = LoggerFactory.getLogger("TestLogger");

    @Autowired
    private CustomerService customerService;

    @Test
    @DisplayName("Positive create customer test")
    @Bean
    public void createCustomerPositiveTest() {

        Customer customer = new Customer();
        logger.info(customer.toString());
        this.customerService.add(customer);

    }
}
