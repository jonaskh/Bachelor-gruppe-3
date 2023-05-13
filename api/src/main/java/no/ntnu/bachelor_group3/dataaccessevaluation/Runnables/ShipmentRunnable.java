package no.ntnu.bachelor_group3.dataaccessevaluation.Runnables;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.ShipmentService;

public class ShipmentRunnable implements Runnable{

    private Shipment shipment;

    private ShipmentService shipmentService;

    public ShipmentRunnable(Shipment shipment, ShipmentService shipmentService) {

        this.shipment = shipment;
        this.shipmentService = shipmentService;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public ShipmentService getShipmentService() {
        return shipmentService;
    }

    public void setShipmentService(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @Override
    //this method runs another in order to catch exceptions, else they are ignored.
    public void run() {
        try {
            catchRun();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Simulates the lifecycle of a shipment, where it simulates traveling through different checkpoints
     * to the final terminal. The time is relative to real time.
     */
    public void catchRun() {

        shipmentService.concurrentAdd(shipment);
        //first checkpoint, 2s travel time
        //this.shipmentService.updateCheckpointsOnParcels(shipment, new Checkpoint(shipmentService.getShipmentSenderAddress(shipment), Checkpoint.CheckpointType.Collected));


        //second checkpoint, this goes to first terminal so add the shipment to the terminal queue.
        //this.shipmentService.updateCheckpointsOnParcels(shipment, new Checkpoint(shipmentService.findFirstTerminalToShipment(shipment), Checkpoint.CheckpointType.ReceivedFirstTerminal));

        //this.shipmentService.findFirstTerminalToShipment(shipment).addShipment(shipment);

        //third checkpoint, loaded on car from first terminal
        //this.shipmentService.updateCheckpointsOnParcels(shipment, new Checkpoint(shipmentService.findFirstTerminalToShipment(shipment).getAddress(), Checkpoint.CheckpointType.LoadedOnCar));


        //fourth checkpoint, received at final terminal
        //this.shipmentService.updateCheckpointsOnParcels(shipment, new Checkpoint(shipmentService.findFinalTerminalToShipment(shipment), Checkpoint.CheckpointType.ReceivedFinalTerminal));



        //this.shipmentService.updateCheckpointsOnParcels(shipment, new Checkpoint(shipmentService.findFinalTerminalToShipment(shipment).getAddress(), Checkpoint.CheckpointType.LoadedOnCar));


        //this.shipmentService.updateCheckpointsOnParcels(shipment, new Checkpoint(shipmentService.findFinalTerminalToShipment(shipment).getAddress(), Checkpoint.CheckpointType.UnderDelivery));
    }
}
