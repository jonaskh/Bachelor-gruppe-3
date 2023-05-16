package no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.Lifecycle;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Checkpoint;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Customer;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.TestConfiguration;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest(showSql = false)
@Import(TestConfiguration.class) //to load the required beans for the services
public class LifecycleTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ShipmentService shipmentService;

    @Autowired
    private TerminalService terminalService;

    @Autowired
    private ValidPostalCodeService validPostalCodeService;

    @Test
    @DisplayName("Checks the full lifecycle of one shipment to ensure it works as expected")
    public void OneShipmentFullLifecycleTest() {

        validPostalCodeService.ReadCSVFile();


        Customer sender = new Customer("Myrteigen 5", "Jonas", "6300");
        Customer receiver = new Customer("NTNU", "Stian", "0021");
        Shipment shipment = new Shipment(sender, sender, receiver);

        customerService.add(sender);
        customerService.add(receiver);
        shipmentService.addShipment(shipment);

        Checkpoint checkpoint = new Checkpoint(sender.getAddress(), Checkpoint.CheckpointType.Collected);
        Checkpoint checkpoint1 = new Checkpoint(shipment.getFirstTerminal(), Checkpoint.CheckpointType.ReceivedFirstTerminal);
        Checkpoint checkpoint2 = new Checkpoint(shipment.getFirstTerminal(), Checkpoint.CheckpointType.LoadedOnCar);
        Checkpoint checkpoint3 = new Checkpoint(shipment.getFinalTerminal(), Checkpoint.CheckpointType.ReceivedFinalTerminal);
        Checkpoint checkpoint4 = new Checkpoint(receiver.getAddress(), Checkpoint.CheckpointType.UnderDelivery);

        shipmentService.updateCheckpointsOnParcels(shipment, checkpoint);

        shipmentService.updateCheckpointsOnParcels(shipment, checkpoint1);


        shipmentService.updateCheckpointsOnParcels(shipment, checkpoint2);

        shipmentService.updateCheckpointsOnParcels(shipment, checkpoint3);


        System.out.println("shipments passed: " + terminalService.returnTerminalFromZip("6300").getShipmentNumber());
        System.out.println("shipments passed: " + terminalService.returnTerminalFromZip("0021").getShipmentNumber());
        System.out.println("shipments passed: " + terminalService.returnTerminalFromZip("2618").getShipmentNumber());
        System.out.println("checkpoints in parcel: " + shipment.getParcels().get(0).getCheckpoints());



        //System.out.println("Location: " + shipmentService.getLocation(shipment));

        System.out.println(shipmentService.findByID(1L).getParcels().get(0).getCheckpoints());
        System.out.println("shipments in customer: " + customerService.findByID(sender.getCustomerID()).get().getShipments());

    }
}
