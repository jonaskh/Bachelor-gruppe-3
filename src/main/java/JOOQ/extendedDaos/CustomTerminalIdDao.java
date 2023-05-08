package JOOQ.extendedDaos;

import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.TerminalIdDao;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.TerminalId;
import org.jooq.DSLContext;

import java.util.List;

import static no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.Tables.TERMINAL_ID;

public class CustomTerminalIdDao extends TerminalIdDao {

    private final DSLContext dsl;

    public CustomTerminalIdDao(DSLContext dsl) {
        super(dsl.configuration());
        this.dsl = dsl;
    }

    public List<TerminalId> findByTerminalIdAndPostalCode(Integer terminalId, String postalCode) {
        return dsl.selectFrom(TERMINAL_ID)
                .where(TERMINAL_ID.TERMINAL_ID_TERMINAL_ID.eq(terminalId))
                .and(TERMINAL_ID.POSTAL_CODE.eq(postalCode))
                .fetchInto(TerminalId.class);
    }
}
