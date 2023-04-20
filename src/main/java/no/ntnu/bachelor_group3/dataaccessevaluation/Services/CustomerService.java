package no.ntnu.bachelor_group3.dataaccessevaluation.Services;

import jakarta.transaction.Transactional;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Customer;
import no.ntnu.bachelor_group3.dataaccessevaluation.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public String getCustomerInfo(String id){
        Optional<Customer> customer= customerRepository.findById(id);
        String result;
        if (customer.isEmpty()) {
            result  = "No customer with that ID";
        }
        else {
            result = customer.get().toString();
        }

        return result;
    }

    public List<Customer> listAll() {return(List<Customer>) customerRepository.findAll();}

    public void save(Customer customer) {
        customerRepository.save(customer);
    }
}
