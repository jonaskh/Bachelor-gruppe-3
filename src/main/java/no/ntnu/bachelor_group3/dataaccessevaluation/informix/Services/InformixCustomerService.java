package no.ntnu.bachelor_group3.dataaccessevaluation.informix.Services;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Customer;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.dataaccessevaluation.informix.repositories.InformixCustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class InformixCustomerService {

    @Autowired

    private InformixCustomerRepository customerRepository;

    @Autowired
    private InformixShipmentService informixShipmentService;

    private static final Logger logger = LoggerFactory.getLogger("CustomerServiceLogger");

    public Customer findByID(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);

        return customer.orElse(null);
    }

    public Shipment findShipment(Long id) {
        Shipment shipment = informixShipmentService.findByID(id);

        return shipment;
    }

    //saves a customer to the customerepo, and thus the database
    @Transactional
    public boolean add(Customer customer) {
        boolean success = false;

        Customer ifAlreadyExists = findByID(customer.getCustomerID());
        if (ifAlreadyExists == null) {
            customerRepository.save(customer);
            success = true;
        }
        return success;
    }

    //saves a shipment to the customer
    public boolean addShipment(Shipment shipment) {
        boolean success = false;

        Shipment ifAlreadyExists = informixShipmentService.findByID(shipment.getShipment_id());
        if (ifAlreadyExists == null) {
            informixShipmentService.add(shipment);
            success = true;
        }
        return success;
    }
}
