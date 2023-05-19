//package no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.CustomerTests;
//
//import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Customer;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
//
//import no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.TestConfiguration;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Services.CustomerService;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Services.ParcelService;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Services.ShipmentService;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@ExtendWith(SpringExtension.class)
//@DataJpaTest(showSql = false)
//@Import(TestConfiguration.class) //to load the required beans for the services
//public class TestAllEntities {
//
//    @Autowired
//    private CustomerService customerService;
//
//    @Autowired
//    private ShipmentService shipmentService;
//
//    @Autowired
//    private ParcelService parcelService;
//
//    @Test
//    @DisplayName("Ensure number of parcels in shipment equals number of parcels in db")
//    public void addAll() {
//        Customer customer = new Customer("Ålesund", "Stian", "6300");
//        Shipment shipment = new Shipment(customer, customer, customer);
//        shipmentService.addShipment(shipment);
//        System.out.println("Number of parcels in db shipment: " + shipmentService.returnNrOfParcels(shipmentService.findByID(shipment.getShipment_id())));
//        System.out.println("Number of parcels: " + parcelService.count());
//        System.out.println("Number of shipments: " + shipmentService.count());
//        System.out.println("Number of customers: " + customerService.count());
//        assertEquals(shipmentService.returnNrOfParcels(shipmentService.findByID(shipment.getShipment_id())), parcelService.count());
//    }
//}
//
