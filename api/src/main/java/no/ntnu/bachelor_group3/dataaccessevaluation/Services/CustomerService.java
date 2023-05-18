package no.ntnu.bachelor_group3.dataaccessevaluation.Services;

import jakarta.transaction.Transactional;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Customer;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Terminal;
import no.ntnu.bachelor_group3.dataaccessevaluation.Repositories.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class CustomerService {

    private static List<String> customerEval = new ArrayList<>();

    @Autowired
    private CustomerRepository customerRepository;

    //further methods and imports excluded

    public List<String> getCustomerEval() {
        return customerEval;
    }


    //saves a customer to the database
    @Transactional
    public void save(Customer customer) {
        var before = Instant.now();
        customerRepository.save(customer);
        var duration = Duration.between(before, Instant.now()).toNanos();
        customerEval.add(duration + " , customer create");
    }

    //returns one customer by id from database.
    @Transactional
    public Optional<Customer> findByID(Long id) {
        var before = Instant.now();
        Optional<Customer> customerOptional = customerRepository.findById(id);
        var duration = Duration.between(before, Instant.now());
        customerEval.add(duration.get(ChronoUnit.NANOS) + ", customer read");
        return customerOptional;
    }

    //returns total ammount of customers in database
    @Transactional
    public long count() {
        var before = Instant.now();
        var count = customerRepository.count();
        var duration = Duration.between(before, Instant.now());

        customerEval.add(duration.get(ChronoUnit.NANOS) + " , customer read all");
        return count;
    }

    @Transactional
    public Customer findByName(String name) {
        Optional<Customer> customer = customerRepository.findCustomerByName(name);

        return customer.orElse(null);
    }

    @Transactional
    //saves a customer to the customerepo, and thus the database
    public void add(Customer customer) {

        Optional<Customer> ifAlreadyExists = findByID(customer.getCustomerID());
        if (ifAlreadyExists.isEmpty()) {
            var before = Instant.now();
            customerRepository.save(customer);
            var duration = Duration.between(before, Instant.now());
            customerEval.add(duration.get(ChronoUnit.NANOS) + " , customer create");
            System.out.println("Customer: " + customer.getCustomerID() + " has been saved to database");
        }
    }
}
