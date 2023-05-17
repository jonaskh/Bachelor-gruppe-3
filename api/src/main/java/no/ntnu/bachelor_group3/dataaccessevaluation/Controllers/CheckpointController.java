package no.ntnu.bachelor_group3.dataaccessevaluation.Controllers;

import no.ntnu.bachelor_group3.jdbcevaluation.Services.CheckpointDAO;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class CheckpointController {



    CheckpointDAO checkpointDAO = new CheckpointDAO();

    @GetMapping("/checkpoint/eval")
    public List<String> getCheckpointEval() {

        Iterable<String> products = checkpointDAO.getExecutionTimeList();
        List<String> evalList = StreamSupport
                .stream(products.spliterator(), false)
                .collect(Collectors.toList());
        return evalList;
    }
}