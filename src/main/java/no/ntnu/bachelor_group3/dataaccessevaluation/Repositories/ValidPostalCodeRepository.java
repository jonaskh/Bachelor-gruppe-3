package no.ntnu.bachelor_group3.dataaccessevaluation.Repositories;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.ValidPostalCode;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ValidPostalCodeRepository extends CrudRepository<ValidPostalCode, String> {

    //@Query(value = "SELECT * FROM valid_postal_codes WHERE postalCode = ?", nativeQuery = true)
    Optional<ValidPostalCode> findByPostalCode(String zip);
}
