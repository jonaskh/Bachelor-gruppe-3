package no.ntnu.bachelor_group3.dataaccessevaluation.Data;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerRepository extends CrudRepository<Customer, String> {

}
