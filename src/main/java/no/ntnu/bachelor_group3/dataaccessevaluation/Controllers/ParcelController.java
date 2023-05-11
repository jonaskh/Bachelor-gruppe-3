package no.ntnu.bachelor_group3.dataaccessevaluation.Controllers;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Customer;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Parcel;
import no.ntnu.bachelor_group3.dataaccessevaluation.Repositories.CustomerRepository;
import no.ntnu.bachelor_group3.dataaccessevaluation.Repositories.ParcelRepository;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.CustomerService;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.ParcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ParcelController {

    @Autowired
    ParcelService parcelService;

    @Autowired
    ParcelRepository parcelRepository;


    @GetMapping("/parcel/{cellData}")
    public List<Parcel> getParcelForCustomer(@PathVariable("cellData") long cellData) {

        Optional<Parcel> products = parcelRepository.findById(cellData);
        List<Parcel> parcelList = StreamSupport
                .stream(products.stream().spliterator(), false)
                .collect(Collectors.toList());
        return parcelList;
    }
}
