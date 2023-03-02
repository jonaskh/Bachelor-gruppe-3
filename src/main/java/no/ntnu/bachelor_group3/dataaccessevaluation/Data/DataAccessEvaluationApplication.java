package no.ntnu.bachelor_group3.dataaccessevaluation.Data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


@SpringBootApplication
@Configuration
@Component
public class DataAccessEvaluationApplication {

	private static final Logger log = LoggerFactory.getLogger(DataAccessEvaluationApplication.class);


	public static void main(String[] args) {
			SpringApplication.run(DataAccessEvaluationApplication.class, args);
	}




	private void insertValues(CustomerRepository repo) {
		repo.save(new Customer());
	}
}