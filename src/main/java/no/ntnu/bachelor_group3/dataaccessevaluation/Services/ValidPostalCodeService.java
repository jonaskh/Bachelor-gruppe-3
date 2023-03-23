package no.ntnu.bachelor_group3.dataaccessevaluation.Services;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Terminal;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.ValidPostalCode;
import no.ntnu.bachelor_group3.dataaccessevaluation.Repositories.ValidPostalCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ValidPostalCodeService {

    @Autowired
    private  ValidPostalCodeRepository validPostalCodeRepository;

    @Autowired
    private TerminalService terminalService;


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


    //Adds the codes from the CSV to the database.
    public void addCodesFromMap() {
        HashMap<String, ValidPostalCode> mapWithPostalCodes = new HashMap<>();

        mapWithPostalCodes = ReadCSVFile();
        mapWithPostalCodes.forEach((key, value) -> {
            int failed = 0;
            if (validPostalCodeRepository.findByPostalCode(key).isEmpty()) {
                validPostalCodeRepository.save(value);
            } else {
                failed++;
                System.out.println(failed);

            }
            System.out.println("hello");
        });



    }

    public TerminalService getTerminalService() {
        return terminalService;
    }

    /**
     * Stores all zip codes in a hashmap with link to terminal ID. Returns a hashmap where the zip codes are the keys,
     * @return hashmap with keys as zip codes and values as ValidPostalCodes objects with zip, municipality and terminal id.
     */
    public HashMap<String, ValidPostalCode> ReadCSVFile() {
        String csvFile = "Postnummerregister.csv";
        String line = "";
        String cvsSplitBy = ",";

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

        return postalcodes;
    }

    public Terminal returnTerminalFromZip(String zip) {
        Terminal terminal = null;
        if (validPostalCodeRepository.findByPostalCode(zip).isPresent()) {
            terminal = validPostalCodeRepository.findByPostalCode(zip).get().getTerminal_id();
        } else {
            System.out.println("No terminal connected to that zip");
        }
        return terminal;
    }
}
