package no.ntnu.bachelor_group3.dataaccessevaluation.Controllers;

import no.ntnu.bachelor_group3.dataaccessevaluation.Services.CheckpointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class CheckpointController {

    @Autowired
    CheckpointService checkpointService;

    @GetMapping("/checkpoint/eval")
    public List<String> getCheckpointEval() {

        Iterable<String> products = checkpointService.getCheckpointEvals();
        List<String> evalList = StreamSupport
                .stream(products.spliterator(), false)
                .collect(Collectors.toList());
        return evalList;
    }
}
