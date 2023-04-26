package no.ntnu.bachelor_group3.dataaccessevaluation.Runnables;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.ShipmentService;

import java.util.concurrent.Callable;

public class ShipmentCallable implements Callable<String> {

    private Shipment shipment;

    private ShipmentService shipmentService;

    public ShipmentCallable(Shipment shipment, ShipmentService shipmentService) {

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
    public String call() throws Exception {
        String result = "";
        try {
        //    result = catchRun();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Simulates the lifecycle of a shipment, where it simulates traveling through different checkpoints
     * to the final terminal. The time is relative to real time.
     */
//    public String catchRun() {
//
//        return shipmentService.cascadingAddCallable(shipment);
//    }
}
