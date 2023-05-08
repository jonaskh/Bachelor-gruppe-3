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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("ALL")
public class ShipmentSim {
    Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");
    DSLContext dslContext = DSL.using(conn, SQLDialect.POSTGRES);

    CustomerRepository customerRepository = new CustomerRepository(dslContext);
    ValidPostalCodesRepository validPostalCodesRepository = new ValidPostalCodesRepository(dslContext);
    TerminalIdRepository terminalIdRepository = new TerminalIdRepository(dslContext);

    String postalCode1 = validPostalCodesRepository.getRandomZipCode();
    String postalCode2 = validPostalCodesRepository.getRandomZipCode();

    CustomerService customerService = new CustomerService(new CustomerDao(dslContext.configuration()));
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime expectedDeliveryDate = now.plusDays(7);

    ShipmentService shipmentService = new ShipmentService(new ShipmentDao(dslContext.configuration()));
    List<Long> timeTakenList = new CopyOnWriteArrayList<>(); // Use a thread-safe List implementation
// Cre

    public ShipmentSim() throws SQLException {
    }

    public void simulate() {
        Map<String, Integer> terminalIdsByPostalCode = terminalIdRepository.getAllTerminalIdsByPostalCode();

        // Create two customers, sender and receiver
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

        ExecutorService executor = Executors.newFixedThreadPool(10);
        int shipmentCount = 500; // Define the number of shipments to create
        for (int i = 0; i < shipmentCount; i++) {
            executor.submit(new ShipmentRunnable(savedSender, savedReceiver, terminalIdsByPostalCode));
        }
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long total = 0;
        for (Long timeTaken : timeTakenList) {
            total += timeTaken;
        }
        double averageTimeTaken = (double) total / timeTakenList.size();
        System.out.println("Average time taken: " + averageTimeTaken + " ns");

        // Calculate highest and lowest times taken
        long highestTimeTaken = Long.MIN_VALUE;
        long lowestTimeTaken = Long.MAX_VALUE;
        for (Long timeTaken : timeTakenList) {
            if (timeTaken > highestTimeTaken) {
                highestTimeTaken = timeTaken;
            }
            if (timeTaken < lowestTimeTaken) {
                lowestTimeTaken = timeTaken;
            }
        }
        System.out.println("Shipment that took the most time amount of time to create: " + highestTimeTaken + " ns");
        System.out.println("Shipment that took the least amount of time to create: " + lowestTimeTaken + " ns");

        // Print the number of shipments created
        System.out.println("Number of shipments created: " + shipmentCount);
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



            String senderPostalCode = savedSender.getZipCode();
                String recieverPostalCode = savedReceiver.getZipCode();
                long shipmentCreatedTime = System.nanoTime(); // get current time before creating shipment in nanoseconds
                Shipment shipment = new Shipment()
                        .setDelivered(false)
                        .setPayerId(savedSender.getCustomerId())
                        .setReceiverId(savedReceiver.getCustomerId())
                        .setSenderId(savedSender.getCustomerId())
                        .setTimeCreated(LocalDateTime.now()) // set time shipment was created
                        .setExpectedDeliveryDate(expectedDeliveryDate)
                        .setStartTerminalId(senderTerminalId)
                        .setEndTerminalId(receiverTerminalId);

                Shipment savedShipment = shipmentService.create(shipment);

                long shipmentSavedTime = System.nanoTime(); // get time after shipment is saved in nanoseconds
                long timeTaken = shipmentSavedTime - shipmentCreatedTime; // calculate time taken
                timeTakenList.add(timeTaken);
                System.out.println("Time taken for shipment " + savedShipment.getShipmentId() + ": " + timeTaken + " ns");

        }


        }
    }





