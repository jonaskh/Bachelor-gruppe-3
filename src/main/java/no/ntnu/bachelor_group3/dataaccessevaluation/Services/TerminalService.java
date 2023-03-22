package no.ntnu.bachelor_group3.dataaccessevaluation.Services;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Customer;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Terminal;
import no.ntnu.bachelor_group3.dataaccessevaluation.Repositories.TerminalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.Optional;


@Service
public class TerminalService {


    TerminalRepository terminalRepository;

    //finds a terminal in database based on id, return null if no matching id
    public Terminal findByID(Long id) {
        return terminalRepository.findById(id).orElse(null);
    }

    public boolean addTerminal(Terminal terminal) {
        Optional<Terminal> existingTerminal = terminalRepository.findById(terminal.getTerminal_id());
        if (existingTerminal.isPresent()) {
            return false;
        } else {
            terminalRepository.save(terminal);
            return true;
        }
    }
}
