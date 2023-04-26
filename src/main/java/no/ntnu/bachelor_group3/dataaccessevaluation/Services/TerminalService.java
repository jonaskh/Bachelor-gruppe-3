package no.ntnu.bachelor_group3.dataaccessevaluation.Services;

import jakarta.transaction.Transactional;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Terminal;
import no.ntnu.bachelor_group3.dataaccessevaluation.Repositories.TerminalRepository;
import no.ntnu.bachelor_group3.dataaccessevaluation.Repositories.ValidPostalCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;


@Service
public class TerminalService {


    @Autowired
    TerminalRepository terminalRepository;

    @Autowired
    ValidPostalCodeRepository validPostalCodeRepository;

    private List<String> terminalEvals = new CopyOnWriteArrayList<>();

    public List<String> getTerminalEvals() {
        return terminalEvals;
    }

    @Transactional
    //finds a terminal in database based on id, return null if no matching id
    public Terminal findByID(Integer id) {
        return terminalRepository.findById(id).orElse(null);
    }

    //checks if terminal with this id exist already, if not add it to database
    @Transactional
    public boolean addTerminal(Terminal terminal) {

        Optional<Terminal> existingTerminal = terminalRepository.findById(terminal.getTerminal_id());
        if (existingTerminal.isPresent()) {
            return false;
        } else {
            terminalRepository.save(terminal);
            System.out.println("Terminal has been added to database");
            return true;
        }
    }

    @jakarta.transaction.Transactional
    public long count() {
        var before = Instant.now();
        var count = terminalRepository.count();
        var duration = Duration.between(before, Instant.now()).toNanos() + " , checkpoint read all";
        terminalEvals.add(duration);
        return count;
    }



    @Transactional
    public Terminal returnTerminalFromZip(String zip) {
        Terminal terminal;
        if (validPostalCodeRepository.findByPostalCode(zip).isPresent()) {
            terminal = validPostalCodeRepository.findByPostalCode(zip).get().getTerminal_id();
        } else {
            System.out.println("No terminal connected to that zip");
            return null;
        }
        return terminal;
    }

}
