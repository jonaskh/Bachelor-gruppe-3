package no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.Lifecycle;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Customer;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.TestConfiguration;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest(showSql = false)
@Import(TestConfiguration.class)
public class SingleThreadTests {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private ShipmentService shipmentService;

    @Autowired
    private TerminalService terminalService;

    @Autowired
    private ValidPostalCodeService validPostalCodeService;

    @Autowired
    private CheckpointService checkpointService;

    @Autowired
    private ParcelService parcelService;

    private static List<String> evals = new ArrayList<>();

    @Test
    public void checkEvals() {

        for (int i = 0; i < 20; i++) {
            Customer customer = new Customer();
            Customer customer2 = new Customer();
            Shipment shipment = new Shipment(customer, customer2, customer);
            shipmentService.addShipment(shipment);
        }

        evals.addAll(shipmentService.getShipmentEvals());
        evals.addAll(parcelService.getParcelEvals());
        evals.addAll(customerService.getCustomerEval());
        evals.addAll(checkpointService.getCheckpointEvals());

        System.out.println("parcels in ship: " + shipmentService.findByID(5L).getParcels().size());

        System.out.println("\n \n shipment count: " + shipmentService.count());
        System.out.println("customer count: " + customerService.count());
        System.out.println("parcel count: " + parcelService.count());
        System.out.println("checkpoint count: " + checkpointService.count());
        System.out.println("terminal count: " + terminalService.count());

    }

    @Test
    public void positiveAndNegativeFindTimerTest() {
        Customer customer = new Customer();
        Shipment shipment = new Shipment(customer, customer, customer);
        shipmentService.add(shipment);

        var before = Instant.now();
        Shipment shipment1 = shipmentService.findByID(shipment.getShipment_id());
        var duration = Duration.between(before, Instant.now()).toNanos();
        System.out.println("Positive find timer: " + duration);

        var before2 = Instant.now();
        Shipment shipment2 = shipmentService.findByID(2L);
        var duration2 = Duration.between(before2, Instant.now()).toNanos();
        System.out.println("Negative find timer: " + duration2);
    }
}
