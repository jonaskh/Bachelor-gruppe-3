package no.ntnu.bachelor_group3.dataaccessevaluation.Services;

import com.github.javafaker.Faker;
import jakarta.transaction.Transactional;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Customer;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Terminal;
import no.ntnu.bachelor_group3.dataaccessevaluation.Repositories.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class CustomerService {

    private static List<String> customerEval = new ArrayList<>();

    @Autowired
    private CustomerRepository customerRepository;


    @Autowired
    private TerminalService terminalService;

    private static final Logger logger = LoggerFactory.getLogger("CustomerServiceLogger");

    public List<String> getCustomerEval() {
        return customerEval;
    }

    @jakarta.transaction.Transactional
    public void save(Customer customer) {
        customerRepository.save(customer);
    }


    @Transactional
    public Optional<Customer> findByID(Long id) {
        var before = Instant.now();
        Optional<Customer> customerOptional = customerRepository.findById(id);
        var duration = Duration.between(before, Instant.now());
        customerEval.add(duration.get(ChronoUnit.NANOS) + ", customer read");
        return customerOptional;
    }

    @Transactional
    public void addShipment(Shipment shipment, Customer customer) {
        findByID(customer.getCustomerID()).get().addShipment(shipment);
    }



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

//    public String findShipmentLocation(Shipment shipment, Customer customer) {
//
//    }

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




    public Terminal findNearestTerminalToCustomer(Customer customer) {
        return terminalService.returnTerminalFromZip(customer.getZip_code());
    }
}
