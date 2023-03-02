package no.ntnu.bachelor_group3.dataaccessevaluation.Data;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

}
