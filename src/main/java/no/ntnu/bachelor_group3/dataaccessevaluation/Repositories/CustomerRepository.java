package no.ntnu.bachelor_group3.dataaccessevaluation.Repositories;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer,String> {
    @Override
    Optional<Customer> findById(String s);
}
