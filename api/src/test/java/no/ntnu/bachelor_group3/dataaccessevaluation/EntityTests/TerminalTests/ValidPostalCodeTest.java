//package no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.TerminalTests;
//
//import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Terminal;
//import no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.TestConfiguration;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Services.TerminalService;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Services.ValidPostalCodeService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@ExtendWith(SpringExtension.class)
//@DataJpaTest(showSql = false)
//@Import(TestConfiguration.class) //to load the required beans for the services
//public class ValidPostalCodeTest {
//
//    @Autowired
//    ValidPostalCodeService validPostalCodeService;
//
//    @Autowired
//    TerminalService terminalService;
//
//
//
//
//    public void createTerminals(){
//
//        String[] terminalAddresses = {"OSLO", "AKERSHUS", "ØSTFOLD", "HEDMARK", "OPPLAND", "BUSKERUD", "VESTFOLD", "TELEMARK",
//                "ROGALAND", "VEST-AGDER", "AUST-AGDER", "HORDALAND", "SOGN OG FJORDANE", "MØRE OG ROMSDAL", "SØR-TRØNDELAG", "NORD-TRØNDELAG",
//                "NORDLAND", "TROMS", "FINNMARK"};
//
//        for (int i=0; i<=17; i++) {
//            Terminal terminal = new Terminal(terminalAddresses[i]);
//            validPostalCodeService.getTerminalService().addTerminal(terminal);
//        }
//    }
//
//    @Test
//    public void AddValidPostalCodesToDatabase() {
//        createTerminals();
//        validPostalCodeService.ReadCSVFile();
//
//        System.out.println(validPostalCodeService.findByZip("6300").toString());
//
//        assertNotNull(validPostalCodeService.findByZip("0021"));
//
//        Terminal terminal = terminalService.returnTerminalFromZip("6300");
//        System.out.println(terminal);
//
//    }
//}
