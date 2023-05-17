package no.ntnu.bachelor_group3.dataaccessevaluation.Runnables;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.CustomerService;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.ShipmentService;

public class AddShipmentsRunnable implements Runnable {
    private Shipment shipment;

    private ShipmentService shipmentService;
    private CustomerService customerService;

    public AddShipmentsRunnable(Shipment shipment, ShipmentService shipmentService, CustomerService customerService) {
        this.shipment = shipment;
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

    public void catchRun() {
        shipmentService.addShipment(shipment);
    }
}
