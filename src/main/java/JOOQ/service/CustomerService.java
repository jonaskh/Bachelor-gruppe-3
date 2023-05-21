package JOOQ.service;

import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.CustomerDao;
import lombok.RequiredArgsConstructor;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Customer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Transactional
@Service(value = "CustomerServiceDAO")
public class CustomerService {

    private final CustomerDao customerDao;

    /**
     * Creates a new Customer.
     *
     * @param customer - the Customer object to be created.
     * @return Customer - the created Customer object.
     */
    public Customer create(Customer customer) {
        customerDao.insert(customer);
        return customer;
    }

    /**
     * Updates an existing Customer.
     *
     * @param customer - the Customer object with updated values.
     * @return Customer - the updated Customer object.
     */
    public Customer update(Customer customer) {
        customerDao.update(customer);
        return customer;
    }

    /**
     * Retrieves all Customers.
     *
     * @return List<Customer> - a list of Customer objects.
     */
    public List<Customer> getAll() {
        return customerDao.findAll();
    }

    /**
     * Retrieves a specific Customer by ID.
     *
     * @param id - the ID of the Customer.
     * @return Customer - the retrieved Customer object.
     * @throws NoSuchElementException if the Customer with the specified ID is not found.
     */
    public Customer getOne(long id) {
        Customer customer = customerDao.findById(id);
        if (customer == null) {
            throw new NoSuchElementException(MessageFormat.format("Customer id {0} not found", String.valueOf(id)));
        }
        return customer;
    }

    /**
     * Deletes a Customer by ID.
     *
     * @param id - the ID of the Customer to be deleted.
     */
    public void deleteById(long id) {
        customerDao.deleteById(id);
    }
}
