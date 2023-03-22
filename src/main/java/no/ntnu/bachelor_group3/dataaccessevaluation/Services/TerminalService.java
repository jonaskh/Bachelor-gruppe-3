package no.ntnu.bachelor_group3.dataaccessevaluation.Services;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Customer;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Terminal;
import no.ntnu.bachelor_group3.dataaccessevaluation.Repositories.TerminalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TerminalService {

    @Autowired
    private TerminalRepository terminalRepository;


    private static final Logger logger = LoggerFactory.getLogger("CustomerServiceLogger");



    //saves a customer to the customerepo, and thus the database
    @Transactional
    public boolean add(Terminal terminal) {
        boolean success = false;

        terminalRepository.save(terminal);
        success = true;

        return success;
    }
}
