package no.ntnu.bachelor_group3.jdbcevaluation.Controllers;

import no.ntnu.bachelor_group3.jdbcevaluation.Services.CheckpointService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class CheckpointController {

    @GetMapping("/checkpoint/evals")
    public List<String> getShipmentEvals() {
        return CheckpointService.executionTimeList;
    }

}