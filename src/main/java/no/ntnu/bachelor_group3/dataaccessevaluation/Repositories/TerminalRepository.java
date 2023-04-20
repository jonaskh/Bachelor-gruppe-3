package no.ntnu.bachelor_group3.dataaccessevaluation.Repositories;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Terminal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TerminalRepository extends CrudRepository<Terminal, Integer> {
    Optional<Terminal> findById(Integer id);


    @Override
    <S extends Terminal> S save(S entity);


    @Override
    <S extends Terminal> Iterable<S> saveAll(Iterable<S> entities);
}
