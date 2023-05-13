package no.ntnu.bachelor_group3.dataaccessevaluation.Services;

import jakarta.transaction.Transactional;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Terminal;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.ValidPostalCode;
import no.ntnu.bachelor_group3.dataaccessevaluation.Repositories.ValidPostalCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ValidPostalCodeService {

    @Autowired
    private  ValidPostalCodeRepository validPostalCodeRepository;

    @Autowired
    private TerminalService terminalService;


    @Transactional
    public ValidPostalCode findByZip(String zip) {
        return validPostalCodeRepository.findByPostalCode(zip).orElse(null);
    }

//    public String addValidPostalCode() {
//        int existing = 0;
//        for (Map.Entry<String, ValidPostalCode> set : ReadCSVFile().entrySet()) {
//            if (validPostalCodeRepository.findByPostalCode(set.getPostalCode()).isPresent()) {
//                existing++;
//            } else {
//                validPostalCodeRepository.save(validPostalCode1);
//
//            }
//        }
//        return "Already existing zip codes: " + existing;
//    }


    @Transactional
    public void createTerminals(){

        String[] terminalAddresses = {"OSLO", "AKERSHUS", "ØSTFOLD", "HEDMARK", "OPPLAND", "BUSKERUD", "VESTFOLD", "TELEMARK",
                "ROGALAND", "VEST-AGDER", "AUST-AGDER", "HORDALAND", "SOGN OG FJORDANE", "MØRE OG ROMSDAL", "SØR-TRØNDELAG", "NORD-TRØNDELAG",
                "NORDLAND", "TROMS", "FINNMARK"};

        for (int i=0; i<terminalAddresses.length; i++) {
            Terminal terminal = new Terminal(terminalAddresses[i]);
            getTerminalService().addTerminal(terminal);
        }
    }

    public TerminalService getTerminalService() {
        return terminalService;
    }

    @Transactional
    public long countAll() {
        return validPostalCodeRepository.count();
    }

    /**
     * Stores all zip codes in a hashmap with link to terminal ID. saves the values in hashmap to database
     * @return hashmap with keys as zip codes and values as ValidPostalCodes objects with zip, municipality and terminal id.
     */
    @Transactional
    public void ReadCSVFile() {
        String csvFile = "./Postnummerregister.csv";
        createTerminals();
        String line = "";
        String cvsSplitBy = ",";
        LocalDateTime start = LocalDateTime.now();

        HashMap<String,ValidPostalCode> postalcodes = new HashMap<>(); // or any other desired size

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            int i = 0;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(cvsSplitBy);
                postalcodes.put(data[0],new ValidPostalCode(data[0], data[4], data[3], terminalService.findByID(Integer.parseInt(data[2]))));
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        LocalDateTime after = LocalDateTime.now();
        long timeTaken = ChronoUnit.MILLIS.between(start,after);
        validPostalCodeRepository.saveAll(postalcodes.values());
    }

}
