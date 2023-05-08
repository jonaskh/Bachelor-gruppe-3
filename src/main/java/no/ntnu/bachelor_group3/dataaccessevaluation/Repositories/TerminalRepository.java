//package no.ntnu.bachelor_group3.dataaccessevaluation.Repositories;
//
//import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Terminal;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//
//@Repository
//public interface TerminalRepository extends CrudRepository<Terminal, Integer> {
//    Optional<Terminal> findById(Integer id);
//
//
////    @Query(value = "select count (distinct (shipment_id, terminal_id)) from terminal where shipment_id = ? and customer_id = ?")
////    long findDistinctByshipment_id(Long id);
//
//    @Override
//    <S extends Terminal> S save(S entity);
//
//
//    @Override
//    <S extends Terminal> Iterable<S> saveAll(Iterable<S> entities);
//}
