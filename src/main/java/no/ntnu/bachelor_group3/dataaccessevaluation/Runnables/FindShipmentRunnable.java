package no.ntnu.bachelor_group3.dataaccessevaluation.Runnables;

import no.ntnu.bachelor_group3.dataaccessevaluation.Services.CustomerService;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.ShipmentService;

public class FindShipmentRunnable implements Runnable {

    private ShipmentService shipmentService;
    private CustomerService customerService;


    public FindShipmentRunnable(ShipmentService shipmentService, CustomerService customerService) {
        this.shipmentService = shipmentService;
        this.customerService = customerService;
    }

    @Override
    public void run() {
        try {
            catchRun();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void catchRun() {
    //    customerService.
    }
}
