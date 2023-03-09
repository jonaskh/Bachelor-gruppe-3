package no.ntnu.bachelor_group3.dataaccessevaluation;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan(basePackages={"no.ntnu.bachelor_group3.dataaccessevaluation.Services"})
@ImportResource({"classpath*:applicationContext.xml"})
public class TestConfiguration{ }

