package no.ntnu.bachelor_group3.dataaccessevaluation.Services;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Terminal;
import no.ntnu.bachelor_group3.dataaccessevaluation.Repositories.TerminalRepository;
import no.ntnu.bachelor_group3.dataaccessevaluation.Repositories.ValidPostalCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class TerminalService {


    @Autowired
    TerminalRepository terminalRepository;

    @Autowired
    ValidPostalCodeRepository validPostalCodeRepository;

    //finds a terminal in database based on id, return null if no matching id
    public Terminal findByID(Integer id) {
        return terminalRepository.findById(id).orElse(null);
    }

    //checks if terminal with this id exist already, if not add it to database
    public boolean addTerminal(Terminal terminal) {

        Optional<Terminal> existingTerminal = terminalRepository.findById(terminal.getTerminal_id());
        if (existingTerminal.isPresent()) {
            return false;
        } else {
            terminalRepository.save(terminal);
            return true;
        }
    }



    public Terminal returnTerminalFromZip(String zip) {
        Terminal terminal = null;
        if (validPostalCodeRepository.findByPostalCode(zip).isPresent()) {
            terminal = validPostalCodeRepository.findByPostalCode(zip).get().getTerminal_id();
        } else {
            System.out.println("No terminal connected to that zip");
        }
        return terminal;
    }

}