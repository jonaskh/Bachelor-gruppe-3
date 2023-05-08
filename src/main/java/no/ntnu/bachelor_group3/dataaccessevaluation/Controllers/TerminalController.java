//package no.ntnu.bachelor_group3.dataaccessevaluation.Controllers;
//
//import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Terminal;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Repositories.TerminalRepository;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Services.TerminalService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.StreamSupport;
//
//@CrossOrigin(origins = "http://localhost:3000")
//@RestController
//public class TerminalController {
//
//    @Autowired
//    TerminalService terminalService;
//
//    @Autowired
//    TerminalRepository terminalRepository;
//
//    @GetMapping("/terminal")
//    public List<Terminal> getAllTerminals() {
//
//        Iterable<Terminal> terminals = terminalRepository.findAll();
//        List<Terminal> terminalList = StreamSupport
//                .stream(terminals.spliterator(), false)
//                .collect(Collectors.toList());
//        return terminalList;
//    }
//}
