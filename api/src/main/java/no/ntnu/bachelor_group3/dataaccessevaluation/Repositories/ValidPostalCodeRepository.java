package no.ntnu.bachelor_group3.dataaccessevaluation.Repositories;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.ValidPostalCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ValidPostalCodeRepository extends CrudRepository<ValidPostalCode, String> {

    Optional<ValidPostalCode> findByPostalCode(String zip);

    long count();
}
