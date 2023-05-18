//package no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.ShipmentTests;
//
//import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Checkpoint;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Customer;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
//
//import no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.TestConfiguration;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Services.CustomerService;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Services.ShipmentService;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Services.TerminalService;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Services.ValidPostalCodeService;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(SpringExtension.class)
//@DataJpaTest
//@Import(TestConfiguration.class) //to load the required beans for the services
//public class CRUDTests {
//
//    @Autowired
//    ShipmentService shipmentService;
//
//    @Autowired
//    CustomerService customerService;
//
//    @Autowired
//    ValidPostalCodeService validPostalCodeService;
//
//    @Autowired
//    TerminalService terminalService;
//
//    @Test
//    @DisplayName("Assert delete method works for shipment, and linked entities are deleted " +
//            "in cascading fashion as well")
//    public void createAndDeleteShipmentTest() {
//        Customer customer = new Customer();
//        Shipment shipment = new Shipment(customer, customer, customer);
//
//        Checkpoint cp1 = new Checkpoint("åasdads", Checkpoint.CheckpointType.Collected);
//        Checkpoint cp2 = new Checkpoint("åasdads", Checkpoint.CheckpointType.Collected);
//
//
//        shipmentService.addShipment(shipment);
//        shipmentService.updateCheckpointsOnParcels(shipmentService.findByID(shipment.getShipment_id()),cp1, cp2);
//        System.out.println(shipmentService.findByID(shipment.getShipment_id()).getParcels().get(0).getCheckpoints());
//    }
//
//    @Test
//    public void deleteShipmentTest() {
//        Customer customer = new Customer();
//        Shipment shipment = new Shipment(customer, customer, customer);
//
//        shipmentService.addShipment(shipment);
//        assertNotNull(shipmentService.findByID(shipment.getShipment_id()));
//
//        System.out.println("customers before delete: " + customerService.count());
//
//        shipmentService.deleteOneShipment(shipment.getShipment_id());
//        assertEquals(0,shipmentService.count());
//
//        System.out.println("customers after delete: " + customerService.count());
//        System.out.println(shipmentService.count());
//    }
//
//    }
