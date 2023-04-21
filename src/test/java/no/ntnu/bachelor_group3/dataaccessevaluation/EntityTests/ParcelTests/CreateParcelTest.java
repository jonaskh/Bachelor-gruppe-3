//package no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.ParcelTests;
//
//
//import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Customer;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Parcel;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
//import no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.TestConfiguration;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Services.CustomerService;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Services.ShipmentService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@ExtendWith(SpringExtension.class)
//@DataJpaTest
//@Import(TestConfiguration.class) //to load the required beans for the services
//public class CreateParcelTest {
//
//    @Autowired
//    ShipmentService shipmentService;
//
//    @Autowired
//    CustomerService customerService;
//
//
//
//    @Test
//    public void addParcelsToShipment() {
//        Customer customer = new Customer();
//        Shipment shipment = new Shipment(customer, customer, customer);
//
//        shipmentService.add(shipment);
//        customerService.add(customer);
//
//
//        assertTrue(shipmentService.findByID(shipment.getShipment_id()) != null);
//
//
//        if (shipmentService.findByID(shipment.getShipment_id()) != null) {
//            shipmentService.addParcels(shipment);
//            System.out.println(shipmentService.printShipmentInfo(shipment));
//        }
//    }
//}
