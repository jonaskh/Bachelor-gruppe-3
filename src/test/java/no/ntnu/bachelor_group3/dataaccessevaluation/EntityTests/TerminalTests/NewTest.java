//package no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.TerminalTests;
//
//import no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.TestConfiguration;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Services.TerminalService;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Services.ValidPostalCodeService;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@ExtendWith(SpringExtension.class)
//@DataJpaTest(showSql = false)
//@Import(TestConfiguration.class) //to load the required beans for the services
//public class NewTest {
//
//    @Autowired
//    ValidPostalCodeService validPostalCodeService;
//
//    @Autowired
//    TerminalService terminalService;
//
//    @Test
//    @DisplayName("Test time to read from csv file to database, and ensure all entries are added without exceptions")
//    public void timeToReadCSVFileTest() {
//
//        validPostalCodeService.ReadCSVFile();
//        System.out.println(validPostalCodeService.findByZip("6300"));
//        assertEquals(5139, validPostalCodeService.countAll());
//    }
//}
