package no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.TerminalTests;

import JOOQ.repositories.TerminalRepository;
import JOOQ.service.TerminalService;
import JOOQ.service.TerminalServiceImpl;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.TerminalDao;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Terminal;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TerminalTestJOOQ {
    private DSLContext dslContext;


    @BeforeEach
    void setUp() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");
        dslContext = DSL.using(conn, SQLDialect.POSTGRES);
    }

    @Test
    void testFindAll() {
        TerminalRepository repository = new TerminalRepository(dslContext);
        List<Terminal> terminals = repository.findAll();
        assertNotNull(terminals);
        assertFalse(terminals.isEmpty());
    }

    @Test
    void testCreateTerminal() {
        Terminal terminal = new Terminal()
                .setAddress("123 Main St.");
        TerminalService service = new TerminalServiceImpl(new TerminalDao(dslContext.configuration()));
        Terminal savedTerminal = service.create(terminal);
        assertNotNull(savedTerminal.getTerminalId());
        assertEquals("123 Main St.", savedTerminal.getAddress());
    }
}








