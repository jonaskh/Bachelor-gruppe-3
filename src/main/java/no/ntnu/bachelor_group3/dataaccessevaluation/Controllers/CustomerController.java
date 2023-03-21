package no.ntnu.bachelor_group3.dataaccessevaluation.Controllers;

import no.ntnu.bachelor_group3.dataaccessevaluation.postgres.Services.PostgresCustomerService;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerController {

    @Autowired
    PostgresCustomerService postgresCustomerService;


}
