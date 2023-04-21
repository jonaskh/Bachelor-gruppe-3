package no.ntnu.bachelor_group3.jdbcevaluation.Data;

public class ValidPostalCode {
    private Long id;

    private Terminal terminal;
    private String area;

    public ValidPostalCode(Long id, Terminal terminal, String area) {
        this.id = id;
        this.terminal = terminal;
        this.area = area;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Long getId() {
        return this.id;
    }
}
