package no.ntnu.bachelor_group3.dataaccessevaluation.Repositories;

<<<<<<< HEAD
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Terminal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Component
public interface TerminalRepository extends CrudRepository<Terminal, Long> {
    Optional<Terminal> findById(Long id);
}
