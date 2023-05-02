package no.ntnu.bachelor_group3.dataaccessevaluation.Runnables;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Checkpoint;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.ShipmentService;

public class LoadedOnCarRunnable implements Runnable{

    private Shipment shipment;

    private ShipmentService shipmentService;
    private Checkpoint cp1;

    public LoadedOnCarRunnable(Shipment shipment, ShipmentService shipmentService) {
        this.shipment = shipment;
        this.shipmentService = shipmentService;
        this.cp1 = cp1;
    }

    @Override
    public void run() {
        shipmentService.updateThirdCheckpointsOnParcels(shipment);
    }
}
