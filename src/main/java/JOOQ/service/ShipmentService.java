package JOOQ.service;

import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.ShipmentDao;



import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Customer;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Shipment;


import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.records.ShipmentRecord;
import org.jooq.DSLContext;
import org.jooq.Query;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import org.jooq.meta.derby.sys.Sys;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

import static no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.Shipment.SHIPMENT;

@Service
@Transactional
public class ShipmentService {
    long nextShipId = 1L; // initial shipment id

    // Variables for logging the time taken for operations

    private final AtomicLong nextId = new AtomicLong(0L);
    private final List<String> timeTakenList;
    // jOOQ's DSLContext enables the construction of SQL queries in a type-safe way
    private final DSLContext dslContext;
    private final List<Long> timeTakenToCreateList;
    private final List<Long> timeTakenToReadList;



    // ShipmentDao is used to interact with the Shipment table in the database
    private final ShipmentDao shipmentDao;

    // Constructor for ShipmentService, initializing lists to hold timing data and the DAO to interact with the database
    public ShipmentService(ShipmentDao shipmentDao, DSLContext dslContext) {
        this.shipmentDao = shipmentDao;
        this.timeTakenList = new ArrayList<>();
        this.dslContext = dslContext;
        this.timeTakenToCreateList = new ArrayList<>();
        this.timeTakenToReadList = new ArrayList<>();
    }

    // Method to create a new Shipment record

    public Shipment createShipment(Customer sender, Customer payer, Customer receiver, int senderTerminalId, int receiverTerminalId) {
        Instant startTime = Instant.now();
        LocalDateTime expectedDeliveryDate = LocalDateTime.now().plusDays(7);
        ShipmentRecord shipmentRecord = dslContext.newRecord(SHIPMENT);
        shipmentRecord.setShipmentId(nextShipId++);
        shipmentRecord.setSenderId(sender.getCustomerId());
        shipmentRecord.setPayerId(payer.getCustomerId());
        shipmentRecord.setReceiverId(receiver.getCustomerId());
        shipmentRecord.setDelivered(false);
        shipmentRecord.setExpectedDeliveryDate(expectedDeliveryDate);
        shipmentRecord.setTimeCreated(LocalDateTime.now());
        shipmentRecord.setStartTerminalId(senderTerminalId);
        shipmentRecord.setEndTerminalId(receiverTerminalId);
        shipmentRecord.attach(dslContext.configuration());
        shipmentRecord.insert();

        Instant endTime = Instant.now();
        Duration duration = Duration.between(startTime, endTime);
        timeTakenList.add(duration.toNanos() + ", shipment, create ");
        timeTakenToCreateList.add(duration.toNanos());
        return shipmentRecord.into(Shipment.class);
    }
    // Method to calculate statistical measures on a list of numbers
    public Map<String, Long> calculateStatistics(List<Long> numbers) {
        Map<String, Long> statistics = new HashMap<>();

        System.out.println(numbers.size());
        // Remove the first 10 values
        List<Long> remainingNumbers = numbers.subList(10, numbers.size());

        // Print the size of the remaining list
        int remainingListSize = remainingNumbers.size();
        System.out.println("Size of the remaining list: " + remainingListSize);

        // Calculate statistics for the remaining numbers
        long min = Collections.min(remainingNumbers);
        long max = Collections.max(remainingNumbers);

        double sum = 0;
        for (long number : remainingNumbers) {
            sum += number;
        }
        double average = sum / remainingNumbers.size();

        double squaredDifferenceSum = 0;
        for (long number : remainingNumbers) {
            double difference = number - average;
            squaredDifferenceSum += difference * difference;
        }
        double variance = squaredDifferenceSum / remainingNumbers.size();
        double standardDeviation = Math.sqrt(variance);

        // Store the statistics
        statistics.put("min", min);
        statistics.put("max", max);
        statistics.put("average", (long) average);
        statistics.put("standardDeviation", (long) standardDeviation);

        // Print the statistics

        return statistics;
    }


    // Getter methods for time taken lists

    public List<Long> getTimeTakenToCreateList() {
        return timeTakenToCreateList;
    }
    public List<Long> getTimeTakenToReadList() {
        return timeTakenToReadList;
    }


    public List<String> getTimeTakenList() {
        return timeTakenList;
    }

    // Method to get all shipment records
    public List<Shipment> getAll() {
        return shipmentDao.findAll();
    }



    public Shipment getOne(long id) {
        Instant startTime = Instant.now();

        Shipment shipment = shipmentDao.findById(id);
        if (shipment == null) {
            throw new NoSuchElementException(MessageFormat.format("Shipment id {0} not found", String.valueOf(id)));
        }

        Instant endTime = Instant.now();
        Duration duration = Duration.between(startTime, endTime);
        timeTakenList.add(duration.toNanos() + ", shipment, read ");
        timeTakenToReadList.add(duration.toNanos());

        return shipment;
    }


    public void deleteById(long id) {
        Instant startTime = Instant.now();

        shipmentDao.deleteById(id);

        Instant endTime = Instant.now();
        Duration duration = Duration.between(startTime, endTime);
        timeTakenList.add(duration.toNanos() + ", shipment, delete ");
    }
    // Method to delete all shipment records
    public void deleteAllShipments() {
        dslContext.deleteFrom(SHIPMENT).execute();
    }


    // ...
}

