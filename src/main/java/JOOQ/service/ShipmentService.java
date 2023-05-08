package JOOQ.service;
import JOOQ.repositories.ShipmentRepository;

import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.ShipmentDao;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.TerminalDao;

import lombok.RequiredArgsConstructor;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Shipment;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

import static no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.Tables.PARCEL;
import static no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.Shipment.SHIPMENT;

@RequiredArgsConstructor
@Transactional
@Service(value = "ShipmentServiceDAO")
public class ShipmentService{

    private final ShipmentDao shipmentDao;

    private ShipmentRepository shipmentRepository;

    public Shipment create(Shipment shipment) {
        Long nextId = shipmentDao.configuration().dsl().select(DSL.max(SHIPMENT.SHIPMENT_ID)).from(SHIPMENT).fetchOneInto(Long.class);
        if (nextId == null) {
            nextId = 1L;
        } else {
            nextId++;
        }
        shipment.setShipmentId(nextId);
        shipmentDao.insert(shipment);
        return shipment;
    }

    public Shipment update(Shipment shipment) {
        shipmentDao.update(shipment);
        return shipment;
    }

    public List<Shipment> getAll() {
        return shipmentDao.findAll();
    }


    public Shipment getOne(long id) {
        Shipment shipment = shipmentDao.findById(id);
        if(shipment == null){
            throw new NoSuchElementException(MessageFormat.format("Shipment id {0} not found", String.valueOf(id)));
        }
        return shipment;
    }

    public void deleteById(long id) {
        shipmentDao.deleteById(id);
    }



}



