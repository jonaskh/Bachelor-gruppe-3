package no.ntnu.bachelor_group3.dataaccessevaluation.Controllers;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Customer;
import no.ntnu.bachelor_group3.dataaccessevaluation.Repositories.CustomerRepository;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("/customer")
    public List<Customer> getAllProducts() {

        Iterable<Customer> products = customerRepository.findAll();
        List<Customer> customerList = StreamSupport
                .stream(products.spliterator(), false)
                .collect(Collectors.toList());
        return customerList;
    }

    @GetMapping("/customer/{cellData}")
    public List<Customer> getAllShipments(@PathVariable("cellData") long cellData) {

        Iterable<Customer> products = customerRepository.findAll();
        List<Customer> customerList = StreamSupport
                .stream(products.spliterator(), false)
                .collect(Collectors.toList());
        return customerList;
    }

    @GetMapping("/customer/eval")
    public List<String> getCustomerEval() {

        Iterable<String> products = customerService.getCustomerEval();
        List<String> evalList = StreamSupport
                .stream(products.spliterator(), false)
                .collect(Collectors.toList());
        return evalList;
    }


}