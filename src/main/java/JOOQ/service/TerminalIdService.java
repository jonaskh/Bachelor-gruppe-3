package JOOQ.service;

// Import statements for various required classes and interfaces

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

// TerminalIdService class, marked as a Spring service and transactional
@Service("TerminalIdServiceDAO")
@Transactional
public class TerminalIdService {
    // Variable for TerminalIdDao, used to interact with the TerminalId table in the database
    private final TerminalIdDao dao;
    // jOOQ's DSLContext enables the construction of SQL queries in a type-safe way
    private final DSLContext dslContext;

    // Constructor for TerminalIdService, initializing DAO and DSLContext
    public TerminalIdService(DSLContext dslContext) {
        this.dslContext = dslContext;
        this.dao = new TerminalIdDao();
    }

    // Method to create a new TerminalId record
    public TerminalId create(TerminalId terminalId) {
        dao.insert(terminalId);
        return terminalId;
    }

    // Method to update an existing TerminalId record
    public TerminalId update(TerminalId terminalId) {
        dao.update(terminalId);
        return terminalId;
    }

    // Method to get all TerminalId records
    public List<TerminalId> getAll() {
        return dao.findAll();
    }

    // Method to get a specific TerminalId by id and postal code
    public TerminalId getOne(Integer terminalId, String postalCode) {
        TerminalId foundTerminalId = (TerminalId) findByTerminalIdAndPostalCode(terminalId, postalCode);
        if (foundTerminalId == null) {
            throw new NoSuchElementException(String.format("Terminal ID with id=%d and postal code=%s not found", terminalId, postalCode));
        }
        return foundTerminalId;
    }

    // Method to find a TerminalId by id and postal code

    public List<TerminalId> findByTerminalIdAndPostalCode(Integer terminalId, String postalCode) {
        return dslContext.selectFrom(TERMINAL_ID)
                .where(TERMINAL_ID.TERMINAL_ID_TERMINAL_ID.eq(terminalId))
                .and(TERMINAL_ID.POSTAL_CODE.eq(postalCode))
                .fetchInto(TerminalId.class);
    }

    // Method to get a map of all TerminalIds by PostalCode

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

