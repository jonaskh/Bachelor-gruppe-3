//package no.ntnu.bachelor_group3.dataaccessevaluation.Controllers;
//
//
//import JOOQ.service.ShipmentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.StreamSupport;
//
//@CrossOrigin(origins = "http://localhost:3000")
//@RestController
//public class ShipmentController {
//
//
//    ShipmentService shipmentService;
//
//    @GetMapping("/shipment/eval")
//    public List<String> getShipmentEval() {
//
//        Iterable<String> products = shipmentService.getTimeTakenList();
//        List<String> evalList = StreamSupport
//                .stream(products.spliterator(), false)
//                .collect(Collectors.toList());
//        return evalList;
//    }
//}
