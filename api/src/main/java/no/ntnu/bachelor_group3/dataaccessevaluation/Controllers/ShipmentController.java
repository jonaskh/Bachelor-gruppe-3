package no.ntnu.bachelor_group3.dataaccessevaluation.Controllers;

import no.ntnu.bachelor_group3.jdbcevaluation.Services.ShipmentDAO;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ShipmentController {



    ShipmentDAO shipmentDAO = new ShipmentDAO();


    @GetMapping("/shipment/eval")
    public List<String> getShipmentEval() {

        Iterable<String> products = shipmentDAO.getExecutionTimeList();
        List<String> evalList = StreamSupport
                .stream(products.spliterator(), false)
                .collect(Collectors.toList());
        return evalList;
    }
}