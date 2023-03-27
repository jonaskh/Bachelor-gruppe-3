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
public class TerminalServiceImpl implements TerminalService {

    private final TerminalDao terminalDao;

    @Override
    public Terminal create(Terminal terminal) {
        terminalDao.insert(terminal);

        return terminal;
    }

    @Override
    public Terminal update(Terminal terminal) {
        terminalDao.update(terminal);
        return terminal;
    }

    @Override
    public List<Terminal> getAll() {
        return terminalDao.findAll();
    }


    @Override
    public Terminal getOne(int id) {
        Terminal terminal = terminalDao.findById(id);
        if(terminal == null){
            throw new NoSuchElementException(MessageFormat.format("Terminal id {0} not found", String.valueOf(id)));
        }
        return terminal;
    }

        @Override
    public void deleteById(int id) {
        terminalDao.deleteById(id);
        }
    }



