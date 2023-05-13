package JOOQ.service;

import JOOQ.repositories.ShipmentRepository;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.ShipmentDao;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Customer;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Shipment;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.records.ShipmentRecord;
import org.jooq.DSLContext;
import org.jooq.Query;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import static no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.Shipment.SHIPMENT;

@Service
@Transactional
public class ShipmentService {

    private final ShipmentDao shipmentDao;
    private final AtomicLong nextId = new AtomicLong(0L);
    private final List<String> timeTakenList;
    private final DSLContext dslContext;




    public ShipmentService(ShipmentDao shipmentDao, List<String> timeTakenList, DSLContext dslContext) {
        this.shipmentDao = shipmentDao;
        this.timeTakenList = timeTakenList;
        this.dslContext = dslContext;

    }

    public Shipment createShipment(Customer sender, Customer payer, Customer receiver, int senderTerminalId, int receiverTerminalId) {
        Instant startTime = Instant.now();
        LocalDateTime expectedDeliveryDate = LocalDateTime.now().plusDays(7);

        ShipmentRecord shipmentRecord = dslContext.newRecord(SHIPMENT);
        shipmentRecord.setShipmentId(getNextShipmentId(dslContext));
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

        //System.out.println("Shipment creation took: " + duration.toNanos() + " ns");

        return shipmentRecord.into(Shipment.class);
    }

    public List<String> getTimeTakenList() {
      return timeTakenList;
    }

    private Long getNextShipmentId(DSLContext dslContext) {
        return shipmentDao.configuration().dsl()
                .fetchOne("SELECT nextval('nextShipmentId')::bigint")
                .into(Long.class);
    }



    public void updateShipmentStatus(Shipment shipment) {
        Instant startTime = Instant.now();

        // Update the status of the shipment
        shipment.setDelivered(true);
        shipmentDao.update(shipment);

        Instant endTime = Instant.now();
        Duration duration = Duration.between(startTime, endTime);
        timeTakenList.add(duration.toNanos() + ", shipment, update ");
    }



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

        return shipment;
    }


    public void deleteById(long id) {
        Instant startTime = Instant.now();

        shipmentDao.deleteById(id);

        Instant endTime = Instant.now();
        Duration duration = Duration.between(startTime, endTime);
        timeTakenList.add(duration.toNanos() + ", shipment, delete ");
    }

    public void deleteAllShipments() {
        dslContext.deleteFrom(SHIPMENT).execute();
    }


}
