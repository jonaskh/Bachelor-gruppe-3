package JOOQ.Simulation;

import JOOQ.repositories.CustomerRepository;
import JOOQ.service.CustomerService;
import JOOQ.service.ShipmentService;
import JOOQ.service.TerminalIdService;
import JOOQ.service.ValidPostalCodeService;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.CustomerDao;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.ShipmentDao;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.ValidPostalCodesDao;
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
import java.util.HashMap;
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
    private TerminalIdService terminalIdService;

    private final CustomerService customerService;
    private final LocalDateTime now;
    private final LocalDateTime expectedDeliveryDate;
    private ShipmentService shipmentService;

    private ValidPostalCodeService validPostalCodesService;

    private List<Map<String, Long>> createStatisticsList;
    private List<Map<String, Long>> readStatisticsList;


    public ShipmentSim(ShipmentService shipmentService,  ValidPostalCodeService validPostalCodesService, TerminalIdService terminalIdService) throws SQLException {
        conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");
        dslContext = DSL.using(conn, SQLDialect.POSTGRES);
        customerRepository = new CustomerRepository(dslContext);
        customerService = new CustomerService(new CustomerDao(dslContext.configuration()));
        now = LocalDateTime.now();
        expectedDeliveryDate = now.plusDays(7);
        List<String> timeTakenList = new CopyOnWriteArrayList<>();
        this.shipmentService = shipmentService;
        this.validPostalCodesService = validPostalCodesService;
        this.terminalIdService = terminalIdService;

        createStatisticsList = new ArrayList<>();
        readStatisticsList = new ArrayList<>();



    }


    public void simulate(int threads, int shipmentCount) {
        shipmentService.deleteAllShipments();


        Map<String, Integer> terminalIdsByPostalCode = terminalIdService.getAllTerminalIdsByPostalCode();

        String postalCode1 = validPostalCodesService.getRandomZipCode();
        String postalCode2 = validPostalCodesService.getRandomZipCode();
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




        ExecutorService executor = Executors.newFixedThreadPool(threads);
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

        ExecutorService getExecutor = Executors.newFixedThreadPool(threads);
        for (int i = 0; i < shipmentCount; i++) {
            getExecutor.submit(new GetShipmentRunnable(shipmentService, i + 1)); // Use i + 1 as the shipment ID
        }
        getExecutor.shutdown();
        try {
            getExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }





        List<Long> timeTakenToCreateList = shipmentService.getTimeTakenToCreateList();
        Map<String, Long> createStatistics = shipmentService.calculateStatistics(timeTakenToCreateList);
        createStatisticsList.add(createStatistics);

        List<Long> timeTakenToReadList = shipmentService.getTimeTakenToReadList();
        Map<String, Long> readStatistics = shipmentService.calculateStatistics(timeTakenToReadList);
        readStatisticsList.add(readStatistics);
    }

        // Calculate and print statistics

        public void printStatistics() {
            System.out.println("===== Create Statistics =====");
            for (int i = 0; i < createStatisticsList.size(); i++) {
                System.out.println("Simulation " + (i + 1) + ":");
                Map<String, Long> createStatistics = createStatisticsList.get(i);
                System.out.println("Min: " + createStatistics.get("min"));
                System.out.println("Max: " + createStatistics.get("max"));
                System.out.println("Standard Deviation: " + createStatistics.get("standardDeviation"));
                System.out.println("Average: " + createStatistics.get("average"));
                System.out.println();
            }

            System.out.println("===== Read Statistics =====");
            for (int i = 0; i < readStatisticsList.size(); i++) {
                System.out.println("Simulation " + (i + 1) + ":");
                Map<String, Long> readStatistics = readStatisticsList.get(i);
                System.out.println("Min: " + readStatistics.get("min"));
                System.out.println("Max: " + readStatistics.get("max"));
                System.out.println("Standard Deviation: " + readStatistics.get("standardDeviation"));
                System.out.println("Average: " + readStatistics.get("average"));
                System.out.println();
            }
        }
    }














    class ShipmentRunnable implements Runnable {
        private final Map<String, Integer> terminalIdsByPostalCode;
        private final ShipmentService shipmentService;

        private final Customer savedSender;
        private final Customer savedReceiver;

        public ShipmentRunnable(
                ShipmentService shipmentService,
                Customer savedSender,
                Customer savedReceiver,
                Map<String, Integer> terminalIdsByPostalCode) {
            this.shipmentService = shipmentService;
            this.savedSender = savedSender;
            this.savedReceiver = savedReceiver;
            this.terminalIdsByPostalCode = terminalIdsByPostalCode;
        }

        @Override
        public void run() {
            int senderTerminalId = terminalIdsByPostalCode.get(savedSender.getZipCode());
            int receiverTerminalId = terminalIdsByPostalCode.get(savedReceiver.getZipCode());

            Shipment savedShipment = shipmentService.createShipment(
                    savedSender, savedSender, savedReceiver, senderTerminalId, receiverTerminalId);
        }
    }

class GetShipmentRunnable implements Runnable {
    private final ShipmentService shipmentService;
    private final long shipmentId;

    public GetShipmentRunnable(ShipmentService shipmentService, long shipmentId) {
        this.shipmentService = shipmentService;
        this.shipmentId = shipmentId;
    }

    @Override
    public void run() {
        shipmentService.getOne(shipmentId);
    }
}



