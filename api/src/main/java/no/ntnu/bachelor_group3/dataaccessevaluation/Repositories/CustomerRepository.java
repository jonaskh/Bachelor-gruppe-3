package no.ntnu.bachelor_group3.dataaccessevaluation.Repositories;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Component
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Optional<Customer> findCustomerByName(String name);


    @Override
    <S extends Customer> S save(S entity);

    @Override
    <S extends Customer> Iterable<S> saveAll(Iterable<S> entities);
}
