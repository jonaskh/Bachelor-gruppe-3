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

@SuppressWarnings("ALL")
public class ShipmentSim {
    private final Connection conn;
    private final DSLContext dslContext;
    private final CustomerRepository customerRepository;
    private final ValidPostalCodesRepository validPostalCodesRepository;
    private final TerminalIdRepository terminalIdRepository;
    private final CustomerService customerService;
    private final LocalDateTime now;
    private final LocalDateTime expectedDeliveryDate;
    private final ShipmentService shipmentService;

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
        Map<String, Integer> terminalIdsByPostalCode = terminalIdRepository.getAllTerminalIdsByPostalCode();

        // Create two customers, sender and receiver
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
            executor.submit(new ShipmentRunnable(savedSender, savedReceiver, terminalIdsByPostalCode));
        }
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        List<String> timeList = shipmentService.getTimeTakenList();


//        Map<String, Double> timeStats = shipmentService.getTimeStats();
//        System.out.println("Average time taken: " + timeStats.get("average") + " ns");
//        System.out.println("Shipment that took the most time amount of time to create: " + timeStats.get("highest") + " ns");
//        System.out.println("Shipment that took the least amount of time to create: " + timeStats.get("lowest") + " ns");
    }


    class ShipmentRunnable implements Runnable {
        private final Map<String, Integer> terminalIdsByPostalCode;


        private final Customer savedSender;
        private final Customer savedReceiver;

        public ShipmentRunnable(Customer savedSender, Customer savedReceiver, Map<String, Integer> terminalIdsByPostalCode) {
            this.savedSender = savedSender;
            this.savedReceiver = savedReceiver;
            this.terminalIdsByPostalCode = terminalIdsByPostalCode;
        }
        @Override
        public void run() {
            int senderTerminalId = terminalIdsByPostalCode.get(savedSender.getZipCode());
            int receiverTerminalId = terminalIdsByPostalCode.get(savedReceiver.getZipCode());

            Shipment savedShipment = shipmentService.createShipment(savedSender, savedSender, savedReceiver, senderTerminalId, receiverTerminalId);
        }

        }
    }





