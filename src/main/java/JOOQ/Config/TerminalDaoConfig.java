package JOOQ.Config;

import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.TerminalDao;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.TerminalIdDao;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TerminalDaoConfig {

    @Autowired
    private DSLContext dslContext; // Assuming you have a bean of DSLContext

    /**
     * Creates and configures a TerminalDao bean.
     *
     * @return TerminalDao - the configured TerminalDao bean.
     */
    @Bean
    public TerminalDao terminalDao() {
        // Instantiate and configure the TerminalDao object
        return new TerminalDao(dslContext.configuration());
    }
}
