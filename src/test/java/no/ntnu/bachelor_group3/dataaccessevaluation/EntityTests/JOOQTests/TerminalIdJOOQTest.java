package no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.JOOQTests;

import JOOQ.repositories.TerminalIdRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.TerminalId;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.Tables.TERMINAL_ID;

public class TerminalIdJOOQTest {

    private DSLContext dslContext;



    @BeforeEach
    void setUp() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");
        dslContext = DSL.using(conn, SQLDialect.POSTGRES);
    }
    @Test
    void testSave() {
        TerminalIdRepository terminalIdRepository = new TerminalIdRepository(dslContext);
        TerminalId terminalId = new TerminalId().setPostalCode("1234");
        TerminalId savedTerminalId = terminalIdRepository.save(terminalId);
        assertThat(savedTerminalId).isNotNull();
        assertThat(savedTerminalId.getTerminalIdTerminalId()).isGreaterThan(0);
    }

    @Test
    void testFindAll() {
        TerminalIdRepository terminalIdRepository = new TerminalIdRepository(dslContext);
        List<TerminalId> terminalIds = terminalIdRepository.findAll();
        assertThat(terminalIds).isNotEmpty();
    }

    @Test
    void testFindById() {
        TerminalIdRepository terminalIdRepository = new TerminalIdRepository(dslContext);
        Optional<TerminalId> optionalTerminalId = terminalIdRepository.findById(1);
        assertThat(optionalTerminalId).isPresent();
        TerminalId terminalId = optionalTerminalId.get();
        assertThat(terminalId.getPostalCode()).isEqualTo("1234");
    }

    @Test
    void testUpdate() {
        TerminalIdRepository terminalIdRepository = new TerminalIdRepository(dslContext);
        Optional<TerminalId> optionalTerminalId = terminalIdRepository.findById(1);
        assertThat(optionalTerminalId).isPresent();
        TerminalId terminalId = optionalTerminalId.get();
        terminalId.setPostalCode("5678");
        TerminalId updatedTerminalId = terminalIdRepository.update(terminalId, terminalId.getTerminalIdTerminalId());
        assertThat(updatedTerminalId).isNotNull();
        assertThat(updatedTerminalId.getPostalCode()).isEqualTo("5678");
    }

    @Test
    void testDeleteById() {
        TerminalIdRepository terminalIdRepository = new TerminalIdRepository(dslContext);
        boolean deleted = terminalIdRepository.deleteById(1);
        assertThat(deleted).isTrue();
    }
}
