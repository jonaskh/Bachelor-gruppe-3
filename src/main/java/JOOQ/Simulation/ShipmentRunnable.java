package JOOQ.Simulation;

import JOOQ.service.ShipmentService;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Customer;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Shipment;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ShipmentRunnable implements Runnable {
    private final Map<String, Integer> terminalIdsByPostalCode;
    private final ShipmentService shipmentService;

    private final Customer savedSender;
    private final Customer savedReceiver;

    public ShipmentRunnable(
            ShipmentService shipmentService,
            Customer savedSender,
            Customer savedReceiver,
            Map<String, Integer> terminalIdsByPostalCode) {
        this.shipmentService = shipmentService;
        this.savedSender = savedSender;
        this.savedReceiver = savedReceiver;
        this.terminalIdsByPostalCode = terminalIdsByPostalCode;
    }

    @Override
    public void run() {
        int senderTerminalId = terminalIdsByPostalCode.get(savedSender.getZipCode());
        int receiverTerminalId = terminalIdsByPostalCode.get(savedReceiver.getZipCode());

        Shipment savedShipment = shipmentService.createShipment(
                savedSender, savedSender, savedReceiver, senderTerminalId, receiverTerminalId);

        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Create separate threads for update, read, and delete operations
        //executor.submit(new UpdateShipmentRunnable(shipmentService, savedShipment));
        executor.submit(new GetShipmentRunnable(shipmentService, savedShipment.getShipmentId()));
        //executor.submit(new DeleteShipmentRunnable(shipmentService, savedShipment.getShipmentId()));

        executor.shutdown();
    }
}



class UpdateShipmentRunnable implements Runnable {
        private final ShipmentService shipmentService;
        private final Shipment shipment;

        public UpdateShipmentRunnable(ShipmentService shipmentService, Shipment shipment) {
            this.shipmentService = shipmentService;
            this.shipment = shipment;
        }

        @Override
        public void run() {
            shipmentService.updateShipmentStatus(shipment);
        }
    }

    class GetShipmentRunnable implements Runnable {
        private final ShipmentService shipmentService;
        private final long shipmentId;

        public GetShipmentRunnable(ShipmentService shipmentService, long shipmentId) {
            this.shipmentService = shipmentService;
            this.shipmentId = shipmentId;
        }

        @Override
        public void run() {
            shipmentService.getOne(shipmentId);
        }
    }

    class DeleteShipmentRunnable implements Runnable {
        private final ShipmentService shipmentService;
        private final long shipmentId;

        public DeleteShipmentRunnable(ShipmentService shipmentService, long shipmentId) {
            this.shipmentService = shipmentService;
            this.shipmentId = shipmentId;
        }

        @Override
        public void run() {
            shipmentService.deleteById(shipmentId);
        }
    }




