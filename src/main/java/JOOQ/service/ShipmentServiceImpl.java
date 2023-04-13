package JOOQ.service;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.ShipmentDao;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.TerminalDao;

import lombok.RequiredArgsConstructor;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Shipment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Transactional
@Service(value = "ShipmentServiceDAO")
public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentDao shipmentDao;

    @Override
    public Shipment create(Shipment shipment) {
        shipmentDao.insert(shipment);

        return shipment;
    }

    @Override
    public Shipment update(Shipment shipment) {
        shipmentDao.update(shipment);
        return shipment;
    }

    @Override
    public List<Shipment> getAll() {
        return shipmentDao.findAll();
    }


    @Override
    public Shipment getOne(long id) {
        Shipment shipment = shipmentDao.findById(id);
        if(shipment == null){
            throw new NoSuchElementException(MessageFormat.format("Shipment id {0} not found", String.valueOf(id)));
        }
        return shipment;
    }

    @Override
    public void deleteById(long id) {
        shipmentDao.deleteById(id);
    }
}



