package JOOQ.service;


import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.TerminalIdDao;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.TerminalId;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.Tables.TERMINAL_ID;

@Service("TerminalIdServiceDAO")
@Transactional
public class TerminalIdService {


    private final TerminalIdDao dao;
    private final DSLContext dslContext;



    public TerminalIdService(DSLContext dslContext) {
        this.dslContext = dslContext;
        this.dao = new TerminalIdDao();    }

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
        TerminalId foundTerminalId = (TerminalId) findByTerminalIdAndPostalCode(terminalId, postalCode);
        if (foundTerminalId == null) {
            throw new NoSuchElementException(String.format("Terminal ID with id=%d and postal code=%s not found", terminalId, postalCode));
        }
        return foundTerminalId;
    }

    public List<TerminalId> findByTerminalIdAndPostalCode(Integer terminalId, String postalCode) {
        return dslContext.selectFrom(TERMINAL_ID)
                .where(TERMINAL_ID.TERMINAL_ID_TERMINAL_ID.eq(terminalId))
                .and(TERMINAL_ID.POSTAL_CODE.eq(postalCode))
                .fetchInto(TerminalId.class);
    }

    public Map<String, Integer> getAllTerminalIdsByPostalCode() {
        return dslContext
                .select(TERMINAL_ID.POSTAL_CODE, TERMINAL_ID.TERMINAL_ID_TERMINAL_ID)
                .from(TERMINAL_ID)
                .fetch()
                .stream()
                .collect(Collectors.toMap(
                        record -> record.get(TERMINAL_ID.POSTAL_CODE),
                        record -> record.get(TERMINAL_ID.TERMINAL_ID_TERMINAL_ID)
                ));
    }

}

