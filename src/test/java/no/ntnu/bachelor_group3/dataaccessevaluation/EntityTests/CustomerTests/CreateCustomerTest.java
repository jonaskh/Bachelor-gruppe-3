package no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.CustomerTests;

import com.github.javafaker.Faker;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Customer;
import no.ntnu.bachelor_group3.dataaccessevaluation.Repositories.CustomerRepository;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.CustomerService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateCustomerTest {

    Faker faker = new Faker(new Locale("nb-NO"));
    private final Logger logger = LoggerFactory.getLogger("TestLogger");

    private Customer customer1;
    private Customer customer2;


    private CustomerService service;

    @BeforeEach
    public void setup() {
         customer1 = new Customer();
    }
    @Test
    @DisplayName("Positive create customer test")
    @Bean
    public void createCustomerPositiveTest() {

        logger.info(customer1.toString());
        service.add(customer1);

        assertNotNull(service.findByID(customer1.getCustomerID()));

    }
}
