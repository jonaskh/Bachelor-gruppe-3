package no.ntnu.bachelor_group3.dataaccessevaluation.Simulation;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.ShipmentService;
import no.ntnu.bachelor_group3.dataaccessevaluation.Runnables.ShipmentRunnable;

import java.util.concurrent.*;

public class SimulationRunner {


    private ExecutorService executor = Executors.newFixedThreadPool(5);


    public SimulationRunner(){
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public void work(Shipment shipment, ShipmentService shipmentService) {

            ShipmentRunnable shipmentRunnable = new ShipmentRunnable(shipment, shipmentService);
            var future = executor.submit(shipmentRunnable);
        }
}



