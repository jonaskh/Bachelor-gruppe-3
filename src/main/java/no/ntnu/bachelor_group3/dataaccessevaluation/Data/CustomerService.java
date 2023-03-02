package no.ntnu.bachelor_group3.dataaccessevaluation.Data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    private static final Logger logger = LoggerFactory.getLogger("CustomerServiceLogger");

    public Customer findByID(int id) {
        Optional<Customer> customer = customerRepository.findById(id);

        return customer.orElse(null);
    }


    //saves a customer to the customerepo, and thus the database
    public boolean add(Customer customer) {
        boolean success = false;

        Customer ifAlreadyExists = findByID(customer.getCustomerID());
        if (ifAlreadyExists == null) {
            customerRepository.save(customer);
            success = true;
        }
        return success;
    }
}
