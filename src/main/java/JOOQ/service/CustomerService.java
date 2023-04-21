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

    private final CustomerDao CustomerDao;

    public Customer create(Customer Customer) {
        CustomerDao.insert(Customer);

        return Customer;
    }

    public Customer update(Customer Customer) {
        CustomerDao.update(Customer);
        return Customer;
    }

    public List<Customer> getAll() {
        return CustomerDao.findAll();
    }


    public Customer getOne(long id) {
        Customer Customer = CustomerDao.findById(id);
        if(Customer == null){
            throw new NoSuchElementException(MessageFormat.format("Customer id {0} not found", String.valueOf(id)));
        }
        return Customer;
    }

    public void deleteById(long id) {
        CustomerDao.deleteById(id);
    }
}



