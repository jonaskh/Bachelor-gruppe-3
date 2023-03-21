package no.ntnu.bachelor_group3.dataaccessevaluation.postgres.repositories;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Parcel;
import org.springframework.data.repository.CrudRepository;

public interface PostgresParcelRepository extends CrudRepository<Parcel, Long> {
}
