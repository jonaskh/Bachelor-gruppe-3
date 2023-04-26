package JOOQ.repositories;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.TerminalId;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.records.TerminalIdRecord;
import org.apache.commons.lang3.ObjectUtils;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.Tables.TERMINAL_ID;


@RequiredArgsConstructor
@Transactional
@Repository
public class TerminalIdRepository implements JOOQRepository<TerminalId>{

    private final DSLContext dslContext;

    @Override
    public TerminalId save(TerminalId terminalId){
        TerminalIdRecord terminalIdRecord = dslContext.insertInto(TERMINAL_ID)
                .set(TERMINAL_ID.POSTAL_CODE, terminalId.getPostalCode())
                .returning(TERMINAL_ID.TERMINAL_ID_TERMINAL_ID).fetchOne();


        if (terminalIdRecord != null) {
            terminalId.setTerminalIdTerminalId(terminalIdRecord.getTerminalIdTerminalId());
            return terminalId;
        }
        return null;
    }

    @Override
    public TerminalId update(TerminalId terminalId, long id) {
        TerminalIdRecord terminalIdRecord = (TerminalIdRecord) dslContext.update(TERMINAL_ID)
                .set(TERMINAL_ID.POSTAL_CODE, terminalId.getPostalCode())
                .where(TERMINAL_ID.TERMINAL_ID_TERMINAL_ID.eq((int) id));

        return (terminalIdRecord != null)  ? terminalId : null;

    }

    @Override
    public List<TerminalId> findAll() {
        return dslContext
                .selectFrom(TERMINAL_ID)
                .fetchInto(TerminalId.class);
    }

    @Override
    public Optional<TerminalId> findById(long id) {
        TerminalId terminalId = dslContext.selectFrom(TERMINAL_ID).where(TERMINAL_ID.TERMINAL_ID_TERMINAL_ID.eq((int) id)).fetchOneInto(TerminalId.class);
        return (ObjectUtils.isEmpty(terminalId)) ? Optional.empty() : Optional.of(terminalId);
    }


    public Optional<TerminalId> findByPostalCode(String postalCode) {
        TerminalId terminalId = dslContext.selectFrom(TERMINAL_ID).where(TERMINAL_ID.POSTAL_CODE.eq(postalCode)).fetchOneInto(TerminalId.class);
        return (ObjectUtils.isEmpty(terminalId)) ? Optional.empty() : Optional.of(terminalId);
    }





    public boolean deleteById(long id) {
        return dslContext.delete(TERMINAL_ID)
                .where(TERMINAL_ID.TERMINAL_ID_TERMINAL_ID.eq((int) id))
                .execute() == 1;
    }



}
