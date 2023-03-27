package no.ntnu.bachelor_group3.dataaccessevaluation.Data;

import jakarta.persistence.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Entity
@Table(name = "valid_postal_codes")
public class ValidPostalCode {

    @Id
    private String postalCode;

    private String municipality;

    private String county;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "terminal_id")
    private Terminal terminal_id;

    public ValidPostalCode() {
    }

    public ValidPostalCode(String postalCode, String municipality, String county, Terminal terminal_id) {
        this.postalCode = postalCode;
        this.municipality = municipality;
        this.county = county;
        this.terminal_id = terminal_id;
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

    @Override
    public String toString() {
        return "ValidPostalCode{" +
                ", postalCode='" + postalCode + '\'' +
                ", municipality='" + municipality + '\'' +
                ", county='" + county + '\'' +
                ", terminal_id=" + terminal_id +
                '}';
    }
}
