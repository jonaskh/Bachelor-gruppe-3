package no.ntnu.bachelor_group3.dataaccessevaluation.Services;

import com.github.javafaker.Faker;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Customer;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Terminal;
import no.ntnu.bachelor_group3.dataaccessevaluation.Repositories.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class CustomerService {

    private static final Faker faker = new Faker(new Locale("nb-NO"));

    @Autowired
    private CustomerRepository customerRepository;


    @Autowired
    private TerminalService terminalService;

    private static final Logger logger = LoggerFactory.getLogger("CustomerServiceLogger");

    public List<Customer> listAll() {return(List<Customer>) customerRepository.findAll();}

    public void save(Customer customer) {
        customerRepository.save(customer);
    }


    public Optional<Customer> findByID(Long id) {
        return customerRepository.findById(id);
    }

    public void printShipments(Customer customer) {
        if (findByID(customer.getCustomerID()).isPresent()) {
            System.out.println(findByID(customer.getCustomerID()).get().getShipments().values());
        }

    }


    @jakarta.transaction.Transactional
    public long count() {
        return customerRepository.count();
    }

    public Customer findByName(String name) {
        Optional<Customer> customer = customerRepository.findCustomerByName(name);

        return customer.orElse(null);
    }

    @Transactional
    //saves a customer to the customerepo, and thus the database
    public boolean add(Customer customer) {
        boolean success = false;

        Optional<Customer> ifAlreadyExists = findByID(customer.getCustomerID());
        if (ifAlreadyExists.isEmpty()) {
            customerRepository.save(customer);
            System.out.println("Customer: " + customer.getCustomerID() + " has been saved to database");
            success = true;
        }
        return success;
    }




    public Terminal findNearestTerminalToCustomer(Customer customer) {
        return terminalService.returnTerminalFromZip(customer.getZip_code());
    }
}
