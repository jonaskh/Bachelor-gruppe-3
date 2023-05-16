package no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.CustomerTests;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Checkpoint;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Customer;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.TestConfiguration;
import no.ntnu.bachelor_group3.dataaccessevaluation.Repositories.CustomerRepository;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.CustomerService;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.ShipmentService;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.ValidPostalCodeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest(showSql = false)
@Import(TestConfiguration.class) //to load the required beans for the services
public class CreateCustomerTest {

        @Autowired
        private CustomerRepository customerRepositoryTest;

        @Autowired
        private CustomerService customerService;

        @Autowired
        private ShipmentService shipmentService;

        @Autowired
        private ValidPostalCodeService validPostalCodeService;


        @Test
        public void PositiveAddCustomerTest() {
            Customer customer = new Customer("Ålesund", "Stian", "6008");
            customerService.add(customer);
            assertNotNull(customerRepositoryTest.findCustomerByName("Stian"));
        }

        @Test
        @DisplayName("adds a customer to the database, and then creates a shipment and assert that the shipment " +
                "has the correct values")
        public void addShipmentTest() {
            validPostalCodeService.ReadCSVFile();

            Customer customer = new Customer("Ålesund", "Stian", "6008");
            customerService.add(customer);

            Shipment shipment = new Shipment(customer, customer, customer);
            shipmentService.addShipment(shipment);
            assertNotNull(shipmentService.findByID(shipment.getShipment_id()));

            shipmentService.updateCheckpointsOnParcels(shipment,
                    new Checkpoint(shipment.getFirstTerminal(),
                            Checkpoint.CheckpointType.ReceivedFirstTerminal));
            assertNotNull(shipmentService.getLastCheckpoint(shipment));
        }

        @Test
        public void addCheckpointToParcel() {
            Customer customer = new Customer("Ålesund", "Stian", "6008");

            customerService.add(customer);

            Shipment shipment = new Shipment(customer, customer, customer);
            shipmentService.addShipment(shipment);
            shipment.printParcels();
            Checkpoint checkpoint = new Checkpoint("NTNU", Checkpoint.CheckpointType.Collected);
            shipmentService.updateCheckpointsOnParcels(shipment, checkpoint);
            shipment.printParcels();

        }

    }
