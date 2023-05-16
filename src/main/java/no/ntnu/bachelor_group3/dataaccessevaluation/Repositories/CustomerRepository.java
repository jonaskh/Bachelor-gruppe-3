package no.ntnu.bachelor_group3.dataaccessevaluation.Repositories;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Checkpoint;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Optional<Customer> findCustomerByName(String name);

    @Query(value = "SELECT * from customer WHERE name = ?", nativeQuery = true)
    Optional<Customer> findCustomerByNameCustom(String name);

    @Override
    <S extends Customer> S save(S entity);


    @Override
    Optional<Customer> findById(Long aLong);

    @Override
    <S extends Customer> Iterable<S> saveAll(Iterable<S> entities);
}
