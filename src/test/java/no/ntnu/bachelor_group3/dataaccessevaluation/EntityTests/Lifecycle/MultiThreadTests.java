package no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.Lifecycle;

import no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.TestConfiguration;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.*;
import no.ntnu.bachelor_group3.dataaccessevaluation.Simulation.SimulationRunner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest(showSql = false)
@Import(TestConfiguration.class) //to load the required beans for the services
public class MultiThreadTests {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ShipmentService shipmentService;

    @Autowired
    private TerminalService terminalService;

    @Autowired
    private ValidPostalCodeService validPostalCodeService;

    @Autowired
    private CheckpointService checkpointService;

    private SimulationRunner simulationRunner = new SimulationRunner();


    @Test
    @DisplayName("Ensure the runnables work as expected in the thread pool")
    public void concurrencyTest() {
        simulationRunner.runExecutors();
    }
}