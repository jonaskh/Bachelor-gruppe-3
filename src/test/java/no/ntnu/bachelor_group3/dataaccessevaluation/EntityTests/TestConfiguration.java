package no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests;

import no.ntnu.bachelor_group3.dataaccessevaluation.Services.*;
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

    @Bean
    public ValidPostalCodeService validPostalCodeService() {
        return new ValidPostalCodeService();
    }

    @Bean
    public ParcelService parcelService() {
        return new ParcelService();
    }
}