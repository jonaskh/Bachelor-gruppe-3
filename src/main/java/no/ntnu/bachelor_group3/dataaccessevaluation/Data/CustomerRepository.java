package no.ntnu.bachelor_group3.dataaccessevaluation.Data;

import jakarta.persistence.EntityManager;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CustomerRepository extends CrudRepository<customer, String> {

}
