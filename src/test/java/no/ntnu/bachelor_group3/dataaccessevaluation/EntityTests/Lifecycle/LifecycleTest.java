//<<<<<<< HEAD
////package no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.Lifecycle;
////
////import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Checkpoint;
////import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Customer;
////import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
////import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Terminal;
////import no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.TestConfiguration;
////import no.ntnu.bachelor_group3.dataaccessevaluation.Services.*;
////import org.junit.jupiter.api.DisplayName;
////import org.junit.jupiter.api.Test;
////import org.junit.jupiter.api.extension.ExtendWith;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
////import org.springframework.context.annotation.Import;
////import org.springframework.test.context.junit.jupiter.SpringExtension;
////
////@ExtendWith(SpringExtension.class)
////@DataJpaTest(showSql = false)
////@Import(TestConfiguration.class) //to load the required beans for the services
////public class LifecycleTest {
////
////    @Autowired
////    private CustomerService customerService;
////
////    @Autowired
////    private ShipmentService shipmentService;
////
////    @Autowired
////    private TerminalService terminalService;
////
////    @Autowired
////    private ValidPostalCodeService validPostalCodeService;
////
////    @Autowired
////    private CheckpointService checkpointService;
////
////    @Autowired
////    private ParcelService parcelService;
////
////
////    public void createTerminals(){
////
////        String[] terminalAddresses = {"OSLO", "AKERSHUS", "ØSTFOLD", "HEDMARK", "OPPLAND", "BUSKERUD", "VESTFOLD", "TELEMARK",
////                "ROGALAND", "VEST-AGDER", "AUST-AGDER", "HORDALAND", "SOGN OG FJORDANE", "MØRE OG ROMSDAL", "SØR-TRØNDELAG", "NORD-TRØNDELAG",
////                "NORDLAND", "TROMS", "FINNMARK"};
////
////
////        for (int i=0; i<=17; i++) {
////            Terminal terminal = new Terminal(terminalAddresses[i]);
////            validPostalCodeService.getTerminalService().addTerminal(terminal);
////        }
////    }
////
////    @Test
////    @DisplayName("Checks the full lifecycle of one shipment to ensure it works as expected")
////    public void OneShipmentFullLifecycleTest() {
////
////        validPostalCodeService.ReadCSVFile();
////
////        System.out.println(validPostalCodeService.findByZip("6300").toString());
////
////        Customer sender = new Customer("Myrteigen 5", "Jonas", "6300");
////        Customer receiver = new Customer("NTNU", "Stian", "6008");
////        Shipment shipment = new Shipment(sender, sender, receiver);
////
//////        customerService.add(sender);
//////        customerService.add(receiver);
////        shipmentService.add(shipment);
////
////        Checkpoint checkpoint = new Checkpoint(sender.getAddress(), Checkpoint.CheckpointType.Collected);
////        Checkpoint checkpoint1 = new Checkpoint(terminalService.returnTerminalFromZip(sender.getZip_code()), Checkpoint.CheckpointType.ReceivedFirstTerminal);
////        Checkpoint checkpoint2 = new Checkpoint(terminalService.returnTerminalFromZip(sender.getZip_code()), Checkpoint.CheckpointType.LoadedOnCar);
////        Checkpoint checkpoint3 = new Checkpoint(terminalService.returnTerminalFromZip(receiver.getZip_code()), Checkpoint.CheckpointType.ReceivedFinalTerminal);
////        Checkpoint checkpoint4 = new Checkpoint(receiver.getAddress(), Checkpoint.CheckpointType.UnderDelivery);
////
////        shipmentService.updateCheckpointsOnParcels(shipmentService.findByID(shipment.getShipment_id()), checkpoint);
////        shipmentService.findByID(shipment.getShipment_id()).printParcels();
////
////
////
////
////        shipmentService.updateCheckpointsOnParcels(shipmentService.findByID(shipment.getShipment_id()), checkpoint1);
////        shipmentService.findByID(shipment.getShipment_id()).printParcels();
////
////
////
////        shipmentService.updateCheckpointsOnParcels(shipmentService.findByID(shipment.getShipment_id()), checkpoint2);
////        shipmentService.findByID(shipment.getShipment_id()).printParcels();
////
////
////
////        shipmentService.updateCheckpointsOnParcels(shipmentService.findByID(shipment.getShipment_id()), checkpoint3);
////        shipmentService.findByID(shipment.getShipment_id()).printParcels();
////
////    }
////}
//=======
//package no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.Lifecycle;
//
//import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Checkpoint;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Customer;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Terminal;
//import no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.TestConfiguration;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Services.*;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//@ExtendWith(SpringExtension.class)
//@DataJpaTest(showSql = false)
//@Import(TestConfiguration.class) //to load the required beans for the services
//public class LifecycleTest {
//
//    @Autowired
//    private CustomerService customerService;
//
//    @Autowired
//    private ShipmentService shipmentService;
//
//    @Autowired
//    private TerminalService terminalService;
//
//    @Autowired
//    private ValidPostalCodeService validPostalCodeService;
//
//    @Autowired
//    private CheckpointService checkpointService;
//
//    @Autowired
//    private ParcelService parcelService;
//
//
//    public void createTerminals(){
//
//        String[] terminalAddresses = {"OSLO", "AKERSHUS", "ØSTFOLD", "HEDMARK", "OPPLAND", "BUSKERUD", "VESTFOLD", "TELEMARK",
//                "ROGALAND", "VEST-AGDER", "AUST-AGDER", "HORDALAND", "SOGN OG FJORDANE", "MØRE OG ROMSDAL", "SØR-TRØNDELAG", "NORD-TRØNDELAG",
//                "NORDLAND", "TROMS", "FINNMARK"};
//
//
//        for (int i=0; i<=17; i++) {
//            Terminal terminal = new Terminal(terminalAddresses[i]);
//            validPostalCodeService.getTerminalService().addTerminal(terminal);
//        }
//    }
//
//    @Test
//    @DisplayName("Checks the full lifecycle of one shipment to ensure it works as expected")
//    public void OneShipmentFullLifecycleTest() {
//
//        validPostalCodeService.ReadCSVFile();
//
//
//        Customer sender = new Customer("Myrteigen 5", "Jonas", "6300");
//        Customer receiver = new Customer("NTNU", "Stian", "0021");
//        Shipment shipment = new Shipment(sender, sender, receiver);
//
////        customerService.add(sender);
////        customerService.add(receiver);
//        shipmentService.cascadingAdd(shipment);
//
//        Checkpoint checkpoint = new Checkpoint(sender.getAddress(), Checkpoint.CheckpointType.Collected);
//        Checkpoint checkpoint1 = new Checkpoint(terminalService.returnTerminalFromZip(sender.getZip_code()), Checkpoint.CheckpointType.ReceivedFirstTerminal);
//        Checkpoint checkpoint2 = new Checkpoint(terminalService.returnTerminalFromZip(sender.getZip_code()), Checkpoint.CheckpointType.LoadedOnCar);
//        Checkpoint checkpoint3 = new Checkpoint(terminalService.returnTerminalFromZip(receiver.getZip_code()), Checkpoint.CheckpointType.ReceivedFinalTerminal);
//        Checkpoint checkpoint4 = new Checkpoint(receiver.getAddress(), Checkpoint.CheckpointType.UnderDelivery);
//
//        shipmentService.updateCheckpointsOnParcels(shipment, checkpoint);
//
//        shipmentService.updateCheckpointsOnParcels(shipment, checkpoint1);
//
//
//        shipmentService.updateCheckpointsOnParcels(shipment, checkpoint2);
//
//        shipmentService.updateCheckpointsOnParcels(shipment, checkpoint3);
//
//
//        System.out.println("shipments passed: " + terminalService.returnTerminalFromZip("6300").getShipmentNumber());
//        System.out.println("shipments passed: " + terminalService.returnTerminalFromZip("0021").getShipmentNumber());
//        System.out.println("shipments passed: " + terminalService.returnTerminalFromZip("2618").getShipmentNumber());
//        System.out.println("checkpoints in parcel: " + shipmentService.findByID(1L).getParcels().get(0).getCheckpoints().size());
//        System.out.println("checkpoints in parcel: " + shipment.getParcels().get(0).getCheckpoints().size());
//
//
//
//        //System.out.println("Location: " + shipmentService.getLocation(shipment));
//
//        System.out.println(shipmentService.findByID(1L).getParcels().get(0).getCheckpoints());
//
//    }
//}
//>>>>>>> postgresql/springjpa
