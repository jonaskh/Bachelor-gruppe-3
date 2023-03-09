package no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.CustomerTests;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Customer;
import no.ntnu.bachelor_group3.dataaccessevaluation.Repositories.CustomerRepository;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CreateCustomerTest {

        @Autowired
        private CustomerRepository customerRepositoryTest;


        @Test
        public void PositiveAddCustomerTest() {
            Customer customer = new Customer("Ålesund", "Stian", "6008");
            customerRepositoryTest.save(customer);

            assertNotNull(customerRepositoryTest.findCustomerByName("Stian"));
        }

    }
