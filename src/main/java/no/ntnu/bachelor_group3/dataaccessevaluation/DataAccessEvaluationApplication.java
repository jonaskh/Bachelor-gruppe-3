//package no.ntnu.bachelor_group3.dataaccessevaluation;
//
//import no.ntnu.bachelor_group3.dataaccessevaluation.Services.*;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Simulation.SimulationRunner;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//
//import java.time.Duration;
//import java.time.Instant;
//import java.util.ArrayList;
//import java.util.List;
//
//
//@SpringBootApplication
//public class DataAccessEvaluationApplication {
//
//	@Autowired
//	private ShipmentService shipmentService;
//
//	@Autowired
//	private TerminalService terminalService;
//
//	@Autowired
//	private CustomerService customerService;
//
//	@Autowired
//	private CheckpointService checkpointService;
//
//	@Autowired
//	private ParcelService parcelService;
//
//	@Autowired
//	private ValidPostalCodeService validPostalCodeService;
//
//	private static final Logger log = LoggerFactory.getLogger(DataAccessEvaluationApplication.class);
//
//	private static List<String> evals = new ArrayList<>();
//
//
//	public static void main(String[] args) {
//		SpringApplication.run(DataAccessEvaluationApplication.class, args);
//	}
//
//	@Bean
//	public void runSimulation() {
//		SimulationRunner simulationRunner = new SimulationRunner(shipmentService, customerService, terminalService, validPostalCodeService, parcelService, checkpointService);
//
//		validPostalCodeService.ReadCSVFile();
//		var before = Instant.now();
//		simulationRunner.simulate();
//		var duration = Duration.between(before, Instant.now()).getSeconds();
//		System.out.println("Duration: " + duration + " seconds");
//
//	}
//
//
//
//
//
//}



/*	@Bean
	public Customer addCustomerToDataBase(CustomerService service) {
		Customer customer = new Customer("sdasd","asdas","1999");

		service.add(customer);
		service.findByID(customer.getCustomerID());

		System.out.println(service.findByID(customer.getCustomerID()).toString());

		return service.findByID(customer.getCustomerID());
	}*/




package no.ntnu.bachelor_group3.dataaccessevaluation;

import JOOQ.Simulation.ShipmentSim;
import JOOQ.service.ShipmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.sql.SQLException;

@SpringBootApplication
@ComponentScan(basePackages = {"JOOQ"})
public class DataAccessEvaluationApplication implements ApplicationRunner {

	private static final Logger log = LoggerFactory.getLogger(DataAccessEvaluationApplication.class);

	@Autowired
	private ShipmentService shipmentService;

	public static void main(String[] args) {
		SpringApplication.run(DataAccessEvaluationApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		ShipmentSim shipmentSim = new ShipmentSim(shipmentService);
		shipmentSim.simulate();
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

/*	@Bean
	public Customer addCustomerToDataBase(CustomerService service) {
		Customer customer = new Customer("sdasd","asdas","1999");

		service.add(customer);
		service.findByID(customer.getCustomerID());

		System.out.println(service.findByID(customer.getCustomerID()).toString());

		return service.findByID(customer.getCustomerID());
	}*/