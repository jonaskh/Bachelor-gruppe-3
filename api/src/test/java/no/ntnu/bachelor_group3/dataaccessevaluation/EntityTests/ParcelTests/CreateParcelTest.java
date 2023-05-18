//package no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.ParcelTests;
//
//
//import jakarta.transaction.Transactional;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Customer;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
//import no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.TestConfiguration;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Services.CustomerService;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Services.ParcelService;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Services.ShipmentService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
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
//    @Autowired
//    ParcelService parcelService;
//
//    private static List<String> timestamps = new ArrayList<>();
//
//
//    @Test
//    @Transactional
//    public void addParcelsToShipment() {
//        Customer customer = new Customer();
//        Shipment shipment = new Shipment(new Customer(), new Customer(), new Customer());
//
//        //       customerService.add(customer);
//        shipmentService.addShipment(shipment);
//        assertEquals(shipmentService.findByID(shipment.getShipment_id()).getParcels().size(),3);
//
//        shipment.printParcels();
//        shipmentService.findByID(shipment.getShipment_id()).printParcels();
//
//    }
//}
