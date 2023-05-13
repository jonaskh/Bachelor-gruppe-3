package JOOQ.Simulation;

import JOOQ.repositories.CustomerRepository;
import JOOQ.repositories.TerminalIdRepository;
import JOOQ.repositories.ValidPostalCodesRepository;
import JOOQ.service.CustomerService;
import JOOQ.service.ShipmentService;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.CustomerDao;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.ShipmentDao;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Customer;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Shipment;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.TerminalId;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.meta.derby.sys.Sys;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ShipmentSim {
    private final Connection conn;
    private final DSLContext dslContext;
    private final CustomerRepository customerRepository;
    private final ValidPostalCodesRepository validPostalCodesRepository;
    private final TerminalIdRepository terminalIdRepository;
    private final CustomerService customerService;
    private final LocalDateTime now;
    private final LocalDateTime expectedDeliveryDate;
    private ShipmentService shipmentService;

    public ShipmentSim(ShipmentService shipmentService) throws SQLException {
        conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");
        dslContext = DSL.using(conn, SQLDialect.POSTGRES);
        customerRepository = new CustomerRepository(dslContext);
        validPostalCodesRepository = new ValidPostalCodesRepository(dslContext);
        terminalIdRepository = new TerminalIdRepository(dslContext);
        customerService = new CustomerService(new CustomerDao(dslContext.configuration()));
        now = LocalDateTime.now();
        expectedDeliveryDate = now.plusDays(7);
        List<String> timeTakenList = new CopyOnWriteArrayList<>();
        this.shipmentService = shipmentService;
    }


    public void simulate() {
        shipmentService.deleteAllShipments();

        Map<String, Integer> terminalIdsByPostalCode = terminalIdRepository.getAllTerminalIdsByPostalCode();

        String postalCode1 = validPostalCodesRepository.getRandomZipCode();
        String postalCode2 = validPostalCodesRepository.getRandomZipCode();
        Customer sender = new Customer()
                .setCustomerId(customerRepository.getNextCustomerId())
                .setAddress("Engveien 23")
                .setName("Stian")
                .setZipCode(postalCode1);
        Customer savedSender = customerService.create(sender);

        Customer receiver = new Customer()
                .setCustomerId(customerRepository.getNextCustomerId())
                .setAddress("Frode Rinnans Vei 32")
                .setName("Daniel")
                .setZipCode(postalCode2);
        Customer savedReceiver = customerService.create(receiver);

        ExecutorService executor = Executors.newFixedThreadPool(3);
        int shipmentCount = 10000;
        for (int i = 0; i < shipmentCount; i++) {
            executor.submit(new ShipmentRunnable(
                    shipmentService, savedSender, savedReceiver, terminalIdsByPostalCode));
        }
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Fetch the timeTakenList after the simulation is completed
        List<String> timeList = new ArrayList<>(shipmentService.getTimeTakenList());

        for (String time : timeList) {
            System.out.println(time);
        }
    }


}