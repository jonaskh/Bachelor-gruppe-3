package no.ntnu.bachelor_group3.dataaccessevaluation;


import JOOQ.Simulation.ShipmentSim;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;

import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
public class DataAccessEvaluationApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataAccessEvaluationApplication.class, args);
	}

	@Bean
	public void runSimulation() throws SQLException {
		ShipmentSim shipmentSim = new ShipmentSim();
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