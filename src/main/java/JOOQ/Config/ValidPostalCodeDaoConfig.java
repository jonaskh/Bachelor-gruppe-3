package JOOQ.Config;

import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.CustomerDao;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.ValidPostalCodesDao;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidPostalCodeDaoConfig {

    @Autowired
    private DSLContext dslContext; // Assuming you have a bean of DSLContext

    /**
     * Creates and configures a ValidPostalCodesDao bean.
     *
     * @return ValidPostalCodesDao - the configured ValidPostalCodesDao bean.
     */
    @Bean
    public ValidPostalCodesDao validPostalCodesDao() {
        // Instantiate and configure the ValidPostalCodesDao object
        return new ValidPostalCodesDao(dslContext.configuration());
    }
}
