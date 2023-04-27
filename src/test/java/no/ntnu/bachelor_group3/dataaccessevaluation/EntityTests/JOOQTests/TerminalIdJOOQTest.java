//package no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.JOOQTests;
//
//import JOOQ.repositories.TerminalIdRepository;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.TerminalId;
//import org.jooq.DSLContext;
//import org.jooq.SQLDialect;
//import org.jooq.impl.DSL;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.Tables.TERMINAL_ID;
//
//public class TerminalIdJOOQTest {
//
//    private DSLContext dslContext;
//
//
//
//    @BeforeEach
//    void setUp() throws SQLException {
//        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");
//        dslContext = DSL.using(conn, SQLDialect.POSTGRES);
//    }
//
//    @Test
//    void testFindAll() {
//        TerminalIdRepository terminalIdRepository = new TerminalIdRepository(dslContext);
//        List<TerminalId> terminalIds = terminalIdRepository.findAll();
//        assertThat(terminalIds).isNotEmpty();
//    }
//
//    @Test
//    void testFindByPostalCode() {
//        TerminalIdRepository terminalIdRepository = new TerminalIdRepository(dslContext);
//        Optional<TerminalId> optionalTerminalId = terminalIdRepository.findByPostalCode("0001");
//        assertThat(optionalTerminalId).isPresent();
//        TerminalId terminalId = optionalTerminalId.get();
//        assertThat(terminalId.getPostalCode()).isEqualTo("0001");
//
//    }
//
//
//
//}
