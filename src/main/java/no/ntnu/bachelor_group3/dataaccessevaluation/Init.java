package no.ntnu.bachelor_group3.dataaccessevaluation;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class Init implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

    }
}
