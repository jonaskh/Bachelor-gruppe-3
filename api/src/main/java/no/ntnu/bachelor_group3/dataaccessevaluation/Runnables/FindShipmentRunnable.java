package no.ntnu.bachelor_group3.dataaccessevaluation.Runnables;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.CustomerService;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.ShipmentService;

public class FindShipmentRunnable implements Runnable {

    private ShipmentService shipmentService;
    private Shipment shipment;


    public FindShipmentRunnable(ShipmentService shipmentService, Shipment shipment) {
        this.shipmentService = shipmentService;
        this.shipment = shipment;
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
        System.out.println(shipmentService.findByID(shipment.getShipment_id()).getParcels().get(0).getLastCheckpoint());
    }
}
