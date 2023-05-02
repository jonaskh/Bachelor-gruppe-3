package no.ntnu.bachelor_group3.dataaccessevaluation.Runnables;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.ShipmentService;

public class ReceivedFirstTerminal implements Runnable{

    private Shipment shipment;

    private ShipmentService shipmentService;


    public ReceivedFirstTerminal(Shipment shipment, ShipmentService shipmentService) {
        this.shipment = shipment;
        this.shipmentService = shipmentService;

    }

    @Override
    public void run() {
        shipmentService.updateSecondCheckpointsOnParcels(shipment);
    }
}
