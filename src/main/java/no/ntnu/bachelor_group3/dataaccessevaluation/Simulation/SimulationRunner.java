package no.ntnu.bachelor_group3.dataaccessevaluation.Simulation;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Customer;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.CustomerService;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.ShipmentService;
import no.ntnu.bachelor_group3.dataaccessevaluation.ShipmentRunnable.ShipmentRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class SimulationRunner {

    private ExecutorService executor = Executors.newFixedThreadPool(10);


    public SimulationRunner(){
    }

    public void runExecutors() {

        Customer customer = new Customer("Oslo", "Tarjei", "0021");
        Customer customer2 = new Customer("Ålesund", "Jonas", "6008");

        Shipment shipment = new Shipment(customer, customer, customer2);
        ShipmentService shipmentService = new ShipmentService();

        for (int i = 0; i < 30; i++) {
            ShipmentRunnable shipmentRunnable = new ShipmentRunnable(shipment, shipmentService);
            executor.submit(shipmentRunnable);
        }

    }


}
