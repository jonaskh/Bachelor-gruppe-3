package JOOQ.service;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.TerminalDao;

import lombok.RequiredArgsConstructor;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Terminal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Transactional
@Service(value = "TerminalServiceDAO")
public class TerminalService{

    private final TerminalDao terminalDao;



    public Terminal create(Terminal terminal) {
        terminalDao.insert(terminal);

        return terminal;
    }

    public Terminal update(Terminal terminal) {
        terminalDao.update(terminal);
        return terminal;
    }

    public List<Terminal> getAll() {
        return terminalDao.findAll();
    }


    public Terminal getOne(long id) {
        Terminal terminal = terminalDao.findById((int) id);
        if(terminal == null){
            throw new NoSuchElementException(MessageFormat.format("Terminal id {0} not found", String.valueOf(id)));
        }
        return terminal;
    }


    public void deleteById(long id) {
        terminalDao.deleteById((int) id);
        }
    }





