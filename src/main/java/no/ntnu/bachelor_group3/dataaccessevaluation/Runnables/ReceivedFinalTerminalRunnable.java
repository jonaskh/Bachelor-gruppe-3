package no.ntnu.bachelor_group3.dataaccessevaluation.Runnables;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Checkpoint;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.ShipmentService;

public class ReceivedFinalTerminalRunnable implements Runnable {


    private Shipment shipment;

    private ShipmentService shipmentService;


    public ReceivedFinalTerminalRunnable(Shipment shipment, ShipmentService shipmentService) {
        this.shipment = shipment;
        this.shipmentService = shipmentService;

    }

    @Override
    public void run() {
        shipmentService.updateFourthCheckpointsOnParcels(shipment);
    }
}
