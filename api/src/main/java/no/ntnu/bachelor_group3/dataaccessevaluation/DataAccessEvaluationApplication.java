package no.ntnu.bachelor_group3.dataaccessevaluation;

import no.ntnu.bachelor_group3.jdbcevaluation.DatabaseManager;
import no.ntnu.bachelor_group3.jdbcevaluation.SimulationRunner;
import no.ntnu.bachelor_group3.jdbcevaluation.generators.DataPopulator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class DataAccessEvaluationApplication {

	private static final Logger log = LoggerFactory.getLogger(DataAccessEvaluationApplication.class);



	public static void main(String[] args) {
		SpringApplication.run(DataAccessEvaluationApplication.class, args);
	}

	@Bean
	public void runSimulation() {
		try (DatabaseManager db = new DatabaseManager()) {
			DataPopulator dataPopulator = new DataPopulator(db);
			// Comment out the two lines below if already populated
			db.deleteRowsFromDB();
			dataPopulator.generateTerminals();
			dataPopulator.generateValidPostalCodes();

			SimulationRunner simulationRunner = new SimulationRunner(db);
			simulationRunner.simulate();

		} catch (Exception e) {
			e.printStackTrace();
		}
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