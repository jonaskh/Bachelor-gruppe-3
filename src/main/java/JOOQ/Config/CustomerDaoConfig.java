package JOOQ.Config;

import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.CustomerDao;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerDaoConfig {

    @Autowired
    private DSLContext dslContext; // Assuming you have a bean of DSLContext

    /**
     * Creates and configures a CustomerDao bean.
     *
     * @return CustomerDao - the configured CustomerDao bean.
     */
    @Bean
    public CustomerDao customerDao() {
        // Instantiate and configure the CustomerDao object
        return new CustomerDao(dslContext.configuration());
    }
}
