package JOOQ.Config;

import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.ShipmentDao;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShipmentDaoConfig {

    @Autowired
    private DSLContext dslContext;

    @Bean
    public ShipmentDao shipmentDao() {
        return new ShipmentDao(dslContext.configuration());
    }
}
