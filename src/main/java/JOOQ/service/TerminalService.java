package JOOQ.service;

// Import statements for various required classes and interfaces
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.TerminalDao;
import lombok.RequiredArgsConstructor;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Terminal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;
import java.util.NoSuchElementException;

// Use of Lombok's RequiredArgsConstructor to inject required fields automatically
@RequiredArgsConstructor
// Denotes that this class is a service and its instances are transactional
@Transactional
@Service(value = "TerminalServiceDAO")
public class TerminalService {

    // TerminalDao used to perform database operations related to Terminal entity
    private final TerminalDao terminalDao;

    // Method to create a new Terminal record
    public Terminal create(Terminal terminal) {
        terminalDao.insert(terminal);
        return terminal;
    }

    // Method to update an existing Terminal record
    public Terminal update(Terminal terminal) {
        terminalDao.update(terminal);
        return terminal;
    }

    // Method to retrieve all Terminal records
    public List<Terminal> getAll() {
        return terminalDao.findAll();
    }

    // Method to retrieve a specific Terminal record by id
    public Terminal getOne(long id) {
        Terminal terminal = terminalDao.findById((int) id);
        if(terminal == null){
            throw new NoSuchElementException(MessageFormat.format("Terminal id {0} not found", String.valueOf(id)));
        }
        return terminal;
    }

    // Method to delete a Terminal record by id
    public void deleteById(long id) {
        terminalDao.deleteById((int) id);
    }
}
