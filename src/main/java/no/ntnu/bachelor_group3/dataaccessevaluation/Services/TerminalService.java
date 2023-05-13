package no.ntnu.bachelor_group3.dataaccessevaluation.Services;

import jakarta.transaction.Transactional;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Checkpoint;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Terminal;
import no.ntnu.bachelor_group3.dataaccessevaluation.Repositories.TerminalRepository;
import no.ntnu.bachelor_group3.dataaccessevaluation.Repositories.ValidPostalCodeRepository;
import org.hibernate.annotations.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;


@Service
public class TerminalService {


    @Autowired
    TerminalRepository terminalRepository;

    @Autowired
    ValidPostalCodeRepository validPostalCodeRepository;

    @Autowired
    CheckpointService checkpointService;

    private List<String> terminalEvals = new CopyOnWriteArrayList<>();

    public List<String> getTerminalEvals() {
        return terminalEvals;
    }

    //finds a terminal in database based on id, return null if no matching id
    public Terminal findByID(Integer id) {
        var before = Instant.now();
        Optional<Terminal> terminal = terminalRepository.findById(id);
        var duration = Duration.between(before, Instant.now()).toNanos();
        terminalEvals.add(duration + ", shipment find");
        return terminal.orElse(null);
    }



    //checks if terminal with this id exist already, if not add it to database
    @Transactional
    public boolean addTerminal(Terminal terminal) {

        Optional<Terminal> existingTerminal = terminalRepository.findById(terminal.getTerminal_id());
        if (existingTerminal.isPresent()) {
            return false;
        } else {
            var before = Instant.now();
            terminalRepository.save(terminal);
            var duration = Duration.between(before, Instant.now()).toNanos();
            terminalEvals.add(duration + ", shipment find");
            return true;
        }
    }

    @Transactional
    public void addShipment(Shipment shipment, Terminal terminal) {
        var before = Instant.now();
        terminal.addShipment(shipment);
        var duration = Duration.between(before, Instant.now());
        terminalEvals.add(duration + " terminal update");
    }

    @Transactional
    public void addCheckpoint(Checkpoint checkpoint, Terminal terminal) {
        checkpoint.getTerminal().addCheckpoint(checkpoint);
    }



    public long getShipmentsinTerminal(int terminal_id) {
        long count = 0;
        if (findByID(terminal_id) != null) {
            Terminal terminal = findByID(terminal_id);
            count = terminal.getShipmentNumber();
        } else {
            System.out.println("not found terminal");
        }
        return count;
    }



    @jakarta.transaction.Transactional
    public long count() {
        var before = Instant.now();
        var count = terminalRepository.count();
        var duration = Duration.between(before, Instant.now()).toNanos() + " , checkpoint read all";
        terminalEvals.add(duration);
        return count;
    }

    //TODO: Find all checkpoints at terminal from id



    @Transactional
    public long shipmentsInTerminal(Terminal terminal) {
        return terminal.getShipmentNumber();
    }

    @Transactional
    public Terminal returnTerminalFromZip(String zip) {
        Terminal terminal;
        if (validPostalCodeRepository.findByPostalCode(zip).isPresent()) {
            var before = Instant.now();
            terminal = validPostalCodeRepository.findByPostalCode(zip).get().getTerminal_id();
            var duration = Duration.between(before, Instant.now());
            terminalEvals.add(duration + " terminal read");
        } else {
            System.out.println("No terminal connected to that zip");
            return null;
        }
        return terminal;
    }

}
