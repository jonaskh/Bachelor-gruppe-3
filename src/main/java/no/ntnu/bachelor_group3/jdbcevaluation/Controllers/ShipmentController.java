package no.ntnu.bachelor_group3.jdbcevaluation.Controllers;

import no.ntnu.bachelor_group3.jdbcevaluation.Services.ShipmentService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ShipmentController {

    @GetMapping("/shipment/evals")
    public List<String> getShipmentEvals() {
        return ShipmentService.executionTimeList;
    }

}