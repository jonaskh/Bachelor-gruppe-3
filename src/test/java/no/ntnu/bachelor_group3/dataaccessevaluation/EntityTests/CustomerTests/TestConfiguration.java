package no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.CustomerTests;

import no.ntnu.bachelor_group3.dataaccessevaluation.Services.CustomerService;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.ShipmentService;
import org.springframework.context.annotation.Bean;

public class TestConfiguration {

    @Bean
    public CustomerService customerService() {
        return new CustomerService();
    }

    @Bean
    public ShipmentService shipmentService() {
        return new ShipmentService();
    }
}
