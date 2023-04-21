package no.ntnu.bachelor_group3.dataaccessevaluation;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class DataAccessEvaluationApplication {

	private static final Logger log = LoggerFactory.getLogger(DataAccessEvaluationApplication.class);


	public static void main(String[] args) {
		SpringApplication.run(DataAccessEvaluationApplication.class, args);
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