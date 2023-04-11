package no.ntnu.bachelor_group3.dataaccessevaluation.ShipmentRunnable;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Checkpoint;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class ShipmentRunnable implements Runnable{

    public Shipment shipment;

    public ShipmentService shipmentService;

    public ShipmentRunnable(Shipment shipment, ShipmentService shipmentService) {
        this.shipment = shipment;
        this.shipmentService = shipmentService;
    }
    /**
     * Simulates the lifecycle of a shipment, where it simulates traveling through different checkpoints
     * to the final terminal. The time is relative to real time.
     */
    @Override
    public void run() {
        //first checkpoint, 2s travel time
        this.shipmentService.updateCheckpointsOnParcels(shipment, new Checkpoint(shipmentService.getShipmentSenderAddress(shipment), Checkpoint.CheckpointType.Collected));

        //second checkpoint, this goes to first terminal so add the shipment to the terminal queue.
        this.shipmentService.updateCheckpointsOnParcels(shipment, new Checkpoint(shipmentService.findFirstTerminalToShipment(shipment), Checkpoint.CheckpointType.ReceivedFirstTerminal));
        this.shipmentService.findFirstTerminalToShipment(shipment).addShipment(shipment);

        //third checkpoint, loaded on car from first terminal
        this.shipmentService.updateCheckpointsOnParcels(shipment, new Checkpoint(shipmentService.findFirstTerminalToShipment(shipment).getAddress(), Checkpoint.CheckpointType.LoadedOnCar));

        //fourth checkpoint, received at final terminal
        this.shipmentService.updateCheckpointsOnParcels(shipment, new Checkpoint(shipmentService.findFinalTerminalToShipment(shipment), Checkpoint.CheckpointType.ReceivedFinalTerminal));

        this.shipmentService.updateCheckpointsOnParcels(shipment, new Checkpoint(shipmentService.findFinalTerminalToShipment(shipment).getAddress(), Checkpoint.CheckpointType.LoadedOnCar));

        this.shipmentService.updateCheckpointsOnParcels(shipment, new Checkpoint(shipmentService.findFinalTerminalToShipment(shipment).getAddress(), Checkpoint.CheckpointType.UnderDelivery));




    }
}
