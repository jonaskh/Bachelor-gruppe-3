package no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.TerminalTests;

import no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.TestConfiguration;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.ValidPostalCodeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest(showSql = false)
@Import(TestConfiguration.class) //to load the required beans for the services
public class NewTest {

    @Autowired
    ValidPostalCodeService validPostalCodeService;

    @Test
    public void timeToReadCSVFileTest() {

        validPostalCodeService.ReadCSVFile();
        assertNotNull(validPostalCodeService.findByZip("6300"));
        System.out.println(validPostalCodeService.findByZip("6300"));
    }
}
