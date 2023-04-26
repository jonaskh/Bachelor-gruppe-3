package no.ntnu.bachelor_group3.dataaccessevaluation.Simulation;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.ShipmentService;
import no.ntnu.bachelor_group3.dataaccessevaluation.Runnables.ShipmentRunnable;
import org.apache.commons.lang3.time.StopWatch;

import java.util.concurrent.*;

public class SimulationRunner {


    private ExecutorService executor = Executors.newFixedThreadPool(5);


    public SimulationRunner() {
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public void work(Shipment shipment, ShipmentService shipmentService) {

        ShipmentRunnable shipmentRunnable = new ShipmentRunnable(shipment, shipmentService);
        var future = executor.submit(shipmentRunnable);
    }

    public void runSimulation() {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            long lastPrintTime = 0;

            while (stopWatch.getTime() < 65000) {
                long currentTime = stopWatch.getTime();
                long timeSinceLastPrint = currentTime - lastPrintTime;
                if (timeSinceLastPrint >= 7000) {
                    System.out.println("Sunday");
                    timeSinceLastPrint = currentTime;

                } else if (timeSinceLastPrint >= 6000) {
                    System.out.println("Saturday");
                    timeSinceLastPrint = currentTime;


                }
                else if (timeSinceLastPrint >= 5000) {
                    System.out.println("Friday");
                    timeSinceLastPrint = currentTime;


                }
                else if (timeSinceLastPrint >= 4000) {
                    System.out.println("Thursday");
                    timeSinceLastPrint = currentTime;

                }
                else if (timeSinceLastPrint >= 3000) {
                    System.out.println("Wednesday");
                    timeSinceLastPrint = currentTime;


                }
                else if (timeSinceLastPrint >= 2000) {
                    System.out.println("Tuesday");
                    timeSinceLastPrint = currentTime;


                }
                else if (timeSinceLastPrint >= 1000) {
                    System.out.println("Monday");
                    timeSinceLastPrint = currentTime;


                }
            }

            stopWatch.stop();
        }
}






