//package java.no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.TerminalTests;
//
//import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Terminal;
//import java.no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.TestConfiguration;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Services.TerminalService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@ExtendWith(SpringExtension.class)
//@DataJpaTest
//@Import(TestConfiguration.class) //to load the required beans for the services
//public class CreateTerminalTest {
//    private static final SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    @Autowired
//    private TerminalService terminalService;
//
//
//    @Test
//    public void PositiveAddCustomerToDatabaseTest() {
//        Terminal terminal = new Terminal("Myrteigen 5");
//        terminalService.addTerminal(terminal);
//        System.out.println(terminal.toString());
//
//        Date time = new Date();
//        System.out.println(sdf3.format(time));
//        assertNotNull(terminalService.findByID(terminal.getTerminal_id()));
//
//
//
//    }
//}
