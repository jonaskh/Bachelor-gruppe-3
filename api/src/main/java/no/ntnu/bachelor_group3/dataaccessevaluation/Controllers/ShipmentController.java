package no.ntnu.bachelor_group3.dataaccessevaluation.Controllers;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ShipmentController {

    @Autowired
    ShipmentService shipmentService;

    @GetMapping("/shipment/eval")
    public List<String> getShipmentEval() {

        Iterable<String> products = shipmentService.getShipmentEvals();
        List<String> evalList = StreamSupport
                .stream(products.spliterator(), false)
                .collect(Collectors.toList());
        return evalList;
    }

        @GetMapping("/shipment/{cellData}")
    public List<Shipment> getAllShipments(@PathVariable("cellData") long cellData) {

        List<Shipment> shipments = shipmentService.findByCustomerID(cellData);
        return shipments;
    }
}
