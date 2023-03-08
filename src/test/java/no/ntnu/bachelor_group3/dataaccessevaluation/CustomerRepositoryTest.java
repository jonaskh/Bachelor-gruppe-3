package no.ntnu.bachelor_group3.dataaccessevaluation;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepositoryTest extends CrudRepository<Customer,Long> {
    @Query(value = "SELECT * FROM customer WHERE Customer_id = ?", nativeQuery = true)
    Customer findByCustomer_id(Long id);

    Customer findCustomerByName(String name);
}
