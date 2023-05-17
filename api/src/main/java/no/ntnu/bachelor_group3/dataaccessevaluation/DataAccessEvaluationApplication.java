package no.ntnu.bachelor_group3.dataaccessevaluation;

import no.ntnu.bachelor_group3.dataaccessevaluation.Services.*;
import no.ntnu.bachelor_group3.dataaccessevaluation.Simulation.SimulationRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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


	public static void main(String[] args) {
		SpringApplication.run(DataAccessEvaluationApplication.class, args);
	}

	@Bean
	public void runSimulation() {
		SimulationRunner simulationRunner = new SimulationRunner(shipmentService, customerService, terminalService, parcelService, checkpointService);

		validPostalCodeService.ReadCSVFile();
		var before = Instant.now();
		simulationRunner.simulate();
		var duration = Duration.between(before, Instant.now()).getSeconds();
		System.out.println("Duration: " + duration + " seconds");
	}

}