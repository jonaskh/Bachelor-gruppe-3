//package JOOQ.service;
//import JOOQ.repositories.ShipmentRepository;
//import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.Checkpoint;
//import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.ShipmentDao;
//import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.TerminalDao;
//
//import lombok.RequiredArgsConstructor;
//import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Parcel;
//import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Shipment;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.text.MessageFormat;
//import java.util.List;
//import java.util.NoSuchElementException;
//
//import static no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.Tables.PARCEL;
//
//@RequiredArgsConstructor
//@Transactional
//@Service(value = "ShipmentServiceDAO")
//public class ShipmentService{
//
//    private final ShipmentDao shipmentDao;
//
//    private ShipmentRepository shipmentRepository;
//
//    public Shipment create(Shipment shipment) {
//        shipmentDao.insert(shipment);
//
//        return shipment;
//    }
//
//    public Shipment update(Shipment shipment) {
//        shipmentDao.update(shipment);
//        return shipment;
//    }
//
//    public List<Shipment> getAll() {
//        return shipmentDao.findAll();
//    }
//
//
//    public Shipment getOne(long id) {
//        Shipment shipment = shipmentDao.findById(id);
//        if(shipment == null){
//            throw new NoSuchElementException(MessageFormat.format("Shipment id {0} not found", String.valueOf(id)));
//        }
//        return shipment;
//    }
//
//    public void deleteById(long id) {
//        shipmentDao.deleteById(id);
//    }
//
//
//
//
//}
//
//
//
