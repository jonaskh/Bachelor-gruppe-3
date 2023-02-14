package no.ntnu.bachelor_group3.dataaccessevaluation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication
@Configuration
public class DataAccessEvaluationApplication {
	public static void main(String[] args) {
			SpringApplication.run(DataAccessEvaluationApplication.class, args);
	}
}