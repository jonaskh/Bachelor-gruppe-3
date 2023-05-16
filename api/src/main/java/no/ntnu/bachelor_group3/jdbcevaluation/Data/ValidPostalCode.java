package no.ntnu.bachelor_group3.jdbcevaluation.Data;

public class ValidPostalCode {
    private Long id;

    private Terminal terminal;

    private String postalCode;
    private String county;
    private String municipality;

    public ValidPostalCode(Long id, String postalCode, Terminal terminal, String county, String municipality) {
        this.id = id;
        this.terminal = terminal;
        this.county = county;
        this.municipality = municipality;
        this.postalCode = postalCode;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
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

    public Long getId() {
        return this.id;
    }
}
