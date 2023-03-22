package no.ntnu.bachelor_group3.dataaccessevaluation.Services;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Terminal;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.ValidPostalCode;
import no.ntnu.bachelor_group3.dataaccessevaluation.Repositories.ValidPostalCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

@Service
public class ValidPostalCodeService {

    @Autowired
    private  ValidPostalCodeRepository validPostalCodeRepository;

    @Autowired
    private TerminalService terminalService;


    public ValidPostalCode findByZip(String zip) {
        return validPostalCodeRepository.findByPostalCode(zip).orElse(null);
    }
    public String addValidPostalCode() {
        int existing = 0;
        for (ValidPostalCode validPostalCode1 : ReadCSVFile()) {
            if (validPostalCodeRepository.findByPostalCode(validPostalCode1.getPostalCode()).isPresent()) {
                existing++;
            } else {
                validPostalCodeRepository.save(validPostalCode1);

            }
        }
        return "Already existing zip codes: " + existing;
    }

    public TerminalService getTerminalService() {
        return terminalService;
    }

    public ValidPostalCode[] ReadCSVFile() {
        String csvFile = "Postnummerregister.csv";
        String line = "";
        String cvsSplitBy = ",";

        ValidPostalCode[] postalcodes = new ValidPostalCode[5139]; // or any other desired size

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            int i = 0;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(cvsSplitBy);
                postalcodes[i] = new ValidPostalCode(data[0], data[4], data[3], terminalService.findByID(Integer.parseInt(data[2])));
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
