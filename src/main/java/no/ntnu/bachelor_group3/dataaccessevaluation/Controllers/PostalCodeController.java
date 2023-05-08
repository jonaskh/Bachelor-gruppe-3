//package no.ntnu.bachelor_group3.dataaccessevaluation.Controllers;
//
//import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Customer;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Data.ValidPostalCode;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Repositories.CustomerRepository;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Repositories.ValidPostalCodeRepository;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Services.CustomerService;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Services.ValidPostalCodeService;
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
//public class PostalCodeController {
//
//    @Autowired
//    ValidPostalCodeService validPostalCodeService;
//
//    @Autowired
//    ValidPostalCodeRepository validPostalCodeRepository;
//
//    @GetMapping("/postalcode")
//    public List<ValidPostalCode> getAllPostals() {
//
//        Iterable<ValidPostalCode> postalCodes = validPostalCodeRepository.findAll();
//        List<ValidPostalCode> postalCodeList = StreamSupport
//                .stream(postalCodes.spliterator(), false)
//                .collect(Collectors.toList());
//        return postalCodeList;
//    }
//}
