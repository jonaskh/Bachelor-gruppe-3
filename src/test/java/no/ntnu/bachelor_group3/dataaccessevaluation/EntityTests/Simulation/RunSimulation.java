package no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.Simulation;

import no.ntnu.bachelor_group3.dataaccessevaluation.Simulation.SimulationRunner;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class RunSimulation {

    @Test
    public void runSimulation() {
        SimulationRunner runner = new SimulationRunner();
        runner.runSimulation();
    }

    @Test
    public void instantFormatTest() {
        var before = Instant.now();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        var duration = Duration.between(before, Instant.now());
        System.out.println(duration.getNano());
        System.out.println("\n" + duration);

    }

}
