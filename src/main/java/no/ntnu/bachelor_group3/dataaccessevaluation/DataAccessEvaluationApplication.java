package no.ntnu.bachelor_group3.dataaccessevaluation;

import no.ntnu.bachelor_group3.dataaccessevaluation.Services.*;
import no.ntnu.bachelor_group3.dataaccessevaluation.Simulation.SimulationRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
public class DataAccessEvaluationApplication {

	@Autowired
	private ShipmentService shipmentService;

	@Autowired
	private TerminalService terminalService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CheckpointService checkpointService;

	@Autowired
	private ParcelService parcelService;

	@Autowired
	private ValidPostalCodeService validPostalCodeService;

	private static final Logger log = LoggerFactory.getLogger(DataAccessEvaluationApplication.class);

	private static List<String> evals = new ArrayList<>();


	public static void main(String[] args) {
		SpringApplication.run(DataAccessEvaluationApplication.class, args);
	}

	@Bean
	public void runSimulation() {
		SimulationRunner simulationRunner = new SimulationRunner(shipmentService, customerService, terminalService, validPostalCodeService, parcelService, checkpointService);

		validPostalCodeService.ReadCSVFile();
		var before = Instant.now();
		simulationRunner.work();
		var duration = Duration.between(before, Instant.now()).getSeconds();
		System.out.println("Duration: " + duration);
		System.out.println(this.shipmentService.count());
		System.out.println(this.parcelService.count());
		System.out.println(this.customerService.count());
		System.out.println(this.checkpointService.count());
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