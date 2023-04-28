package no.ntnu.bachelor_group3.jdbcevaluation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadedRunner {
    public static void main(String[] args) {
        int x = 5; // Number of threads
        ExecutorService executorService = Executors.newFixedThreadPool(x);

        SimulationController simulationController = new SimulationController();

        List<Runnable> tasks = new ArrayList<>();
        for (int i = 1; i < 8; i++) {
            // Creating shipments
            for (int j = 0; j < 300; j++) {
                tasks.add(simulationController::createShipmentProcess);
            }
            // Shipment checkpoints
            for (int k = 300 * (i - 1); k < 300 * i; k++) {
                tasks.add(simulationController::runSixCheckpointsOnShipment);
            }
        }

        for (Runnable task : tasks) {
            executorService.submit(task);
        }

        executorService.shutdown();
    }
}
