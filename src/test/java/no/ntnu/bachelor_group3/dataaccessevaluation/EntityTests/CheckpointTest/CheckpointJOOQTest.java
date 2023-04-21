package no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.CheckpointTest;

import JOOQ.repositories.CheckpointRepository;
import JOOQ.service.CheckpointService;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.CheckpointDao;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Checkpoint;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CheckpointJOOQTest {
    private DSLContext dslContext;


    @BeforeEach
    void setUp() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");
        dslContext = DSL.using(conn, SQLDialect.POSTGRES);
    }

    @Test
    public void testFindAll() {
        CheckpointRepository repository = new CheckpointRepository(dslContext);
        List<Checkpoint> Checkpoints = repository.findAll();
        assertNotNull(Checkpoints);
        assertFalse(Checkpoints.isEmpty());
    }

    @Test
    public void testCreateCheckpoint() {



        LocalDateTime now = LocalDateTime.now();
        Checkpoint Checkpoint = new Checkpoint()
                .setCost(100.0)
                .setTime(now)
                .setType((short) 1)
                .setTerminalId(1);
        CheckpointService service = new CheckpointService(new CheckpointDao(dslContext.configuration()));
        Checkpoint savedCheckpoint = service.create(Checkpoint);
        assertNotNull(savedCheckpoint.getCheckpointId());
        assertEquals(100.0, savedCheckpoint.getCost());
    }

}
