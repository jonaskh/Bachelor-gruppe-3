package no.ntnu.bachelor_group3.dataaccessevaluation.postgres.Services;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Customer;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.dataaccessevaluation.postgres.repositories.PostgresCustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class PostgresCustomerService {

    @Autowired
    private PostgresCustomerRepository postgresCustomerRepository;

    @Autowired
    private PostgresShipmentService postgresShipmentService;

    private static final Logger logger = LoggerFactory.getLogger("CustomerServiceLogger");

    public Customer findByID(Long id) {
        Optional<Customer> customer = postgresCustomerRepository.findById(id);

        return customer.orElse(null);
    }

    public Shipment findShipment(Long id) {
        Shipment shipment = postgresShipmentService.findByID(id);

        return shipment;
    }

    //saves a customer to the customerepo, and thus the database
    @Transactional
    public boolean add(Customer customer) {
        boolean success = false;

        Customer ifAlreadyExists = findByID(customer.getCustomerID());
        if (ifAlreadyExists == null) {
            postgresCustomerRepository.save(customer);
            success = true;
        }
        return success;
    }

    //saves a shipment to the customer
    public boolean addShipment(Shipment shipment) {
        boolean success = false;

        Shipment ifAlreadyExists = postgresShipmentService.findByID(shipment.getShipment_id());
        if (ifAlreadyExists == null) {
            postgresShipmentService.add(shipment);
            success = true;
        }
        return success;
    }
}
