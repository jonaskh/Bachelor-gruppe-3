package no.ntnu.bachelor_group3.dataaccessevaluation.Runnables;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Terminal;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.ShipmentService;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.TerminalService;

public class TerminalShipmentsRunnable implements Runnable {

    private ShipmentService shipmentService;
    private TerminalService terminalService;
    private Terminal terminal;


    public TerminalShipmentsRunnable(ShipmentService shipmentService, TerminalService terminalService, Terminal terminal) {
        this.shipmentService = shipmentService;
        this.terminalService = terminalService;
        this.terminal = terminal;
    }

    @Override
    public void run() {
        try {
            catchRun();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void catchRun() {
        System.out.println(terminalService.returnTerminalFromZip("6300"));
    }
}
