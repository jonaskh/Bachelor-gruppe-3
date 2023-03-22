package no.ntnu.bachelor_group3.dataaccessevaluation.Data;

import jakarta.persistence.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Entity
@Table(name = "valid_postal_codes")
public class ValidPostalCodes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postal_id;

    private String postalCode;

    private String municipality;

    private String county;

    @ManyToOne
    @JoinTable(name = "terminal_id")
    private Terminal terminal_id;

    public ValidPostalCodes() {
    }

    public ValidPostalCodes(String postalCode, String municipality, String county, Terminal terminal_id) {
        this.postalCode = postalCode;
        this.municipality = municipality;
        this.county = county;
        this.terminal_id = terminal_id;
    }

    public void ReadCSVFile() {
        String csvFile = "Postnummerregister.csv";
        String line = "";
        String cvsSplitBy = ",";

        ValidPostalCodes[] postalcodes = new ValidPostalCodes[100]; // or any other desired size

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            int i = 0;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(cvsSplitBy);
                String terminal = data[2];
                postalcodes[i] = new ValidPostalCodes(data[0], data[4], data[3], Terminal.getTerminalById(Long.valueOf(data[2])));
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Print the postalcodes array to verify that the code works correctly
        for (int i = 0; i < postalcodes.length; i++) {
            if (postalcodes[i] != null) {
                System.out.println("ValidPostalCode " + i + ": " + postalcodes[i].getTerminal_id() + ", " + postalcodes[i].getPostalCode());
            }
        }
    }

    public Long getPostal_id() {
        return postal_id;
    }

    public void setPostal_id(Long postal_id) {
        this.postal_id = postal_id;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public Terminal getTerminal_id() {
        return terminal_id;
    }

    public void setTerminal_id(Terminal terminal_id) {
        this.terminal_id = terminal_id;
    }
}
