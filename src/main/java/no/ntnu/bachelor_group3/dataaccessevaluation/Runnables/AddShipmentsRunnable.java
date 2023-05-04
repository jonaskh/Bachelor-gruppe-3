package no.ntnu.bachelor_group3.dataaccessevaluation.Runnables;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.ShipmentService;
import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;

public class AddShipmentsRunnable implements Runnable {
    private Shipment shipment;

    private ShipmentService shipmentService;

    public AddShipmentsRunnable(Shipment shipment, ShipmentService shipmentService) {
        this.shipment = shipment;
        this.shipmentService = shipmentService;
    }

    @Override
    public void run() {
        try {
            catchRun();
        } catch (Exception e) {
            System.out.println("Integrity constraint");

        }
    }

    public void catchRun() {
        shipmentService.cascadingAdd(shipment);
    }
}
