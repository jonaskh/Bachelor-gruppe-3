package no.ntnu.bachelor_group3.dataaccessevaluation.Services;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Customer;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.dataaccessevaluation.Repositories.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ShipmentService shipmentService;

    private static final Logger logger = LoggerFactory.getLogger("CustomerServiceLogger");

    public Customer findByID(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);

        return customer.orElse(null);
    }

    public Shipment findShipment(Long id) {
        Shipment shipment = shipmentService.findByID(id);

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

        Shipment ifAlreadyExists = shipmentService.findByID(shipment.getShipment_id());
        if (ifAlreadyExists == null) {
            shipmentService.add(shipment);
            success = true;
        }
        return success;
    }
}
