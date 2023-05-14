package no.ntnu.bachelor_group3.dataaccessevaluation.Controllers;

import no.ntnu.bachelor_group3.jdbcevaluation.Data.Customer;
import no.ntnu.bachelor_group3.jdbcevaluation.DatabaseManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class CustomerController {


    @GetMapping("/customer")
    public List<Customer> getAllProducts() {

        List<Customer> products;
        try (DatabaseManager db = new DatabaseManager()) {
            products = db.getAllCustomers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<Customer> customerList = StreamSupport
                .stream(products.spliterator(), false)
                .collect(Collectors.toList());

        return customerList;
    }



}
