package JOOQ.repositories;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Terminal;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.records.TerminalRecord;
import org.apache.commons.lang3.ObjectUtils;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.Terminal.TERMINAL;


@RequiredArgsConstructor
@Transactional
@Repository
public class TerminalRepository implements JOOQRepository<Terminal>{

    private final DSLContext dslContext;

    @Override
    public Terminal save(Terminal terminal){
        TerminalRecord terminalRecord = dslContext.insertInto(TERMINAL)
                .set(TERMINAL.ADDRESS, terminal.getAddress())
                .returning(TERMINAL.TERMINAL_ID).fetchOne();


        if (terminalRecord != null) {
            terminal.setTerminalId(terminalRecord.getTerminalId());
            return terminal;
        }
        return null;
    }

    @Override
    public Terminal update(Terminal terminal, long id) {
        TerminalRecord terminalRecord = (TerminalRecord) dslContext.update(TERMINAL)
                .set(TERMINAL.ADDRESS, terminal.getAddress())
                .where(TERMINAL.TERMINAL_ID.eq((int) id));

        return (terminalRecord != null)  ? terminal : null;

    }

    @Override
    public List<Terminal> findAll() {
        return dslContext
                .selectFrom(TERMINAL)
                .fetchInto(Terminal.class);
    }

    @Override
    public Optional<Terminal> findById(long id) {
        Terminal terminal = dslContext.selectFrom(TERMINAL).where(TERMINAL.TERMINAL_ID.eq((int) id)).fetchOneInto(Terminal.class);
        return (ObjectUtils.isEmpty(terminal)) ? Optional.empty() : Optional.of(terminal);
    }

    public boolean deleteById(long id) {
        return dslContext.delete(TERMINAL)
                .where(TERMINAL.TERMINAL_ID.eq((int) id))
                .execute() == 1;
    }



}
