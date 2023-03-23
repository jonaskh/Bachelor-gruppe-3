package no.ntnu.bachelor_group3.dataaccessevaluation.Services;

import com.github.javafaker.Faker;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Customer;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.dataaccessevaluation.Repositories.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.Locale;
import java.util.Optional;

@Service
public class CustomerService {

    private static final Faker faker = new Faker(new Locale("nb-NO"));

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ShipmentService shipmentService;

    private static final Logger logger = LoggerFactory.getLogger("CustomerServiceLogger");

    public Customer findByID(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);

        return customer.orElse(null);
    }

    public Customer findByName(String name) {
        Optional<Customer> customer = customerRepository.findCustomerByName(name);

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

    /**
     * Add a shipment to a customer, setting receiver and payer and sender to corresponding ids
     * @param sender_id id of sender customer
     * @param receiver_id id of receiver customer
     * @param payer_id id of payer customer
     * @return if successful or not
     */
    public boolean addShipment(Long sender_id, Long receiver_id, Long payer_id) {
        boolean success = false;

        Optional<Customer> sender = customerRepository.findById(sender_id);
        Optional<Customer> receiver = customerRepository.findById(receiver_id);
        Optional<Customer> payer = customerRepository.findById(payer_id);

        //same sender and receiver
        if (sender.isPresent() && payer.isPresent() && (sender == receiver)) {
            Shipment shipment = new Shipment(sender.get(), payer.get(), receiver.get());
            success = true;
            shipmentService.add(shipment);

        }

        // if receiver is not an existing customer, address and name is generated at random
        //TODO: generate proper zip values
        if (sender.isPresent() && payer.isPresent() && receiver.isEmpty()) {
            Shipment shipment = new Shipment(sender.get(), payer.get(), faker.name().fullName(), faker.address().streetAddress(), faker.address().zipCode());
            success = true;
            shipmentService.add(shipment);
        } else {
            logger.error("Could not add shipment");
        }
        return success;
    }
}
