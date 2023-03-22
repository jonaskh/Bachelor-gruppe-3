package no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests;

import no.ntnu.bachelor_group3.dataaccessevaluation.Services.CheckpointService;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.CustomerService;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.ShipmentService;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.TerminalService;
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

    @Bean
    public TerminalService terminalService() {
        return new TerminalService();
    }

    @Bean
    public CheckpointService checkpointService() {
        return new CheckpointService();
    }
}
