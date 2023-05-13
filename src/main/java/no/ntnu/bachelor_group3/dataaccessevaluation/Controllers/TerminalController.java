package no.ntnu.bachelor_group3.dataaccessevaluation.Controllers;



import no.ntnu.bachelor_group3.jdbcevaluation.Data.Terminal;
import no.ntnu.bachelor_group3.jdbcevaluation.DatabaseManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class TerminalController {


    @GetMapping("/terminal")
    public List<Terminal> getAllTerminals() {

        List<Terminal> terminals;
        try (DatabaseManager db = new DatabaseManager()) {
            terminals = db.getAllTerminals();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<Terminal> terminalList = StreamSupport
                .stream(terminals.spliterator(), false)
                .collect(Collectors.toList());
        return terminalList;
    }
}
