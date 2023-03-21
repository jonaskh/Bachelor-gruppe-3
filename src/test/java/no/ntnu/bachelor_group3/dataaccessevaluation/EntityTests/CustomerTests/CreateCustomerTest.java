package no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.CustomerTests;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Customer;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Parcel;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.dataaccessevaluation.Repositories.CustomerRepository;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.CustomerService;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.ShipmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(TestConfiguration.class) //to load the required beans for the services
public class CreateCustomerTest {

        @Autowired
        private CustomerRepository customerRepositoryTest;


        @Autowired
        private CustomerService customerService;

        @Autowired
        private ShipmentService shipmentService;


        @Test
        public void PositiveAddCustomerTest() {
            Customer customer = new Customer("Ålesund", "Stian", "6008");
            customerService.add(customer);
            System.out.println(customer.toString());

            assertNotNull(customerRepositoryTest.findCustomerByName("Stian"));

        }

        @Test
        public void addShipmentTest() {
            Customer customer = new Customer("Ålesund", "Stian", "6008");

            customerService.add(customer);

            Shipment shipment = new Shipment(customer, customer, customer.getName(), customer.getAddress(), customer.getZip_code());

            //shipment.addParcel(new Parcel(shipment, 28.5));

            shipmentService.add(shipment);

            assertNotNull(shipmentService.findByID(shipment.getShipment_id()));

            System.out.println(shipment.toString());

        }

    }
