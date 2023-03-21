package no.ntnu.bachelor_group3.dataaccessevaluation.informix.repositories;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface InformixCustomerRepository extends CrudRepository<Customer, Long> {

    Customer findCustomerByName(String name);

}
