package no.ntnu.bachelor_group3.dataaccessevaluation.Runnables;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Checkpoint;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.ShipmentService;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.TerminalService;

public class UpdateShipmentRunnable implements Runnable{

    private Shipment shipment;

    private ShipmentService shipmentService;
    private TerminalService terminalService;
    private Checkpoint cp1;
    private Checkpoint cp2;
    private Checkpoint cp3;
    private Checkpoint cp4;
    private Checkpoint cp5;
    private Checkpoint cp6;


    public UpdateShipmentRunnable(Shipment shipment, ShipmentService shipmentService, TerminalService terminalService) {
        this.shipment = shipment;
        this.shipmentService = shipmentService;
        this.terminalService = terminalService;
        this.cp1 = new Checkpoint(shipment.getSender().getZip_code(), Checkpoint.CheckpointType.Collected);
        this.cp2 = new Checkpoint(shipment.getFirstTerminal(), Checkpoint.CheckpointType.ReceivedFirstTerminal);
        this.cp3 = new Checkpoint(shipment.getFirstTerminal(), Checkpoint.CheckpointType.LoadedOnCar);
        this.cp4 = new Checkpoint(shipment.getFinalTerminal(), Checkpoint.CheckpointType.ReceivedFinalTerminal);
        this.cp5 = new Checkpoint(shipment.getFinalTerminal(), Checkpoint.CheckpointType.LoadedOnCar);
        this.cp6 = new Checkpoint(shipment.getReceiver().getAddress(), Checkpoint.CheckpointType.UnderDelivery);
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

        shipmentService.addShipment(shipment);

        shipmentService.updateCheckpointsOnParcels(shipmentService.findByID(shipment.getShipment_id()), cp1);
        shipmentService.updateCheckpointsOnParcels(shipmentService.findByID(shipment.getShipment_id()), cp2);
        shipmentService.updateCheckpointsOnParcels(shipmentService.findByID(shipment.getShipment_id()), cp3);
        shipmentService.updateCheckpointsOnParcels(shipmentService.findByID(shipment.getShipment_id()), cp4);
        shipmentService.updateCheckpointsOnParcels(shipmentService.findByID(shipment.getShipment_id()), cp5);
        shipmentService.updateCheckpointsOnParcels(shipmentService.findByID(shipment.getShipment_id()), cp6);
        shipmentService.setDelivered(shipment);
    }
}
