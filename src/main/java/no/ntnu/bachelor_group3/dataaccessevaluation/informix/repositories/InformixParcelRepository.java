package no.ntnu.bachelor_group3.dataaccessevaluation.informix.repositories;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Parcel;
import org.springframework.data.repository.CrudRepository;

public interface InformixParcelRepository extends CrudRepository<Parcel, Long> {
}
