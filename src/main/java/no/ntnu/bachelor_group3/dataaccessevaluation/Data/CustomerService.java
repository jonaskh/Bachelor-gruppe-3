package no.ntnu.bachelor_group3.dataaccessevaluation.Data;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Customer;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class CustomerService {
    private CustomerRepository customerRepository;

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
}
