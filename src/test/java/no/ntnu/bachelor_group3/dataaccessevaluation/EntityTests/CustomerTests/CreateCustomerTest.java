package no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.CustomerTests;

import com.github.javafaker.Faker;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
public class CreateCustomerTest {

    Faker faker = new Faker(new Locale("nb-NO"));
    private final Logger logger = LoggerFactory.getLogger("TestLogger");

    @Test
    @DisplayName("Positive create customer test")
    public void createCustomerPositiveTest() {
        Customer customer = new Customer(faker.address().streetAddress(), faker.name().fullName(), faker.address().zipCode());
        logger.info(customer.toString());
        Customer customer1 = new Customer(faker.address().streetAddress(), faker.name().fullName(), faker.address().zipCode());
        logger.info(customer1.toString());

    }
}
