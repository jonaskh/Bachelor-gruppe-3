package JOOQ.service;


import JOOQ.extendedDaos.CustomTerminalIdDao;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.TerminalIdDao;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.TerminalId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.Tables.TERMINAL_ID;

@Service("TerminalIdServiceDAO")
@Transactional
public class TerminalIdService {

    private final TerminalIdDao dao;
    private final CustomTerminalIdDao customTerminalIdDao;

    public TerminalIdService(TerminalIdDao dao, CustomTerminalIdDao customTerminalIdDao) {
        this.dao = dao;
        this.customTerminalIdDao = customTerminalIdDao;
    }

    public TerminalId create(TerminalId terminalId) {
        dao.insert(terminalId);
        return terminalId;
    }

    public TerminalId update(TerminalId terminalId) {
        dao.update(terminalId);
        return terminalId;
    }

    public List<TerminalId> getAll() {
        return dao.findAll();
    }

    public TerminalId getOne(Integer terminalId, String postalCode) {
        TerminalId foundTerminalId = (TerminalId) customTerminalIdDao.findByTerminalIdAndPostalCode(terminalId, postalCode);
        if (foundTerminalId == null) {
            throw new NoSuchElementException(String.format("Terminal ID with id=%d and postal code=%s not found", terminalId, postalCode));
        }
        return foundTerminalId;
    }

}

