package no.ntnu.bachelor_group3.dataaccessevaluation;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Customer;
import no.ntnu.bachelor_group3.dataaccessevaluation.informix.Services.InformixCustomerService;
import no.ntnu.bachelor_group3.dataaccessevaluation.informix.repositories.InformixShipmentRepository;
import no.ntnu.bachelor_group3.dataaccessevaluation.postgres.Services.PostgresCustomerService;
import no.ntnu.bachelor_group3.dataaccessevaluation.postgres.Services.PostgresShipmentService;
import no.ntnu.bachelor_group3.dataaccessevaluation.postgres.repositories.PostgresCustomerRepository;
import no.ntnu.bachelor_group3.dataaccessevaluation.informix.repositories.InformixCustomerRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class DataAccessEvaluationApplication {

	private static final Logger log = LoggerFactory.getLogger(DataAccessEvaluationApplication.class);
	private final InformixShipmentRepository informixShipmentRepository;

	public DataAccessEvaluationApplication(InformixShipmentRepository informixShipmentRepository) {
		this.informixShipmentRepository = informixShipmentRepository;
	}


	public static void main(String[] args) {

		SpringApplication.run(DataAccessEvaluationApplication.class, args);
	}

	public void insertValues(PostgresCustomerRepository postrepo, InformixCustomerRepository inf4repo) {
		PostgresShipmentService postgresShipmentService;
		InformixShipmentRepository informixShipmentService;

		PostgresCustomerService postgresCustomerService;
		InformixCustomerService informixCustomerService;

		postrepo.save(new Customer());
		inf4repo.save(new Customer());
	}
}

/*	@Bean
	public Customer addCustomerToDataBase(CustomerService service) {
		Customer customer = new Customer("sdasd","asdas","1999");

		service.add(customer);
		service.findByID(customer.getCustomerID());

		System.out.println(service.findByID(customer.getCustomerID()).toString());

		return service.findByID(customer.getCustomerID());
	}*/