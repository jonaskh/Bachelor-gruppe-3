package no.ntnu.bachelor_group3.dataaccessevaluation;

import com.github.javafaker.Faker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;


@SpringBootApplication
@Configuration
public class DataAccessEvaluationApplication {
	public static void main(String[] args) {
			SpringApplication.run(DataAccessEvaluationApplication.class, args);

	}
}