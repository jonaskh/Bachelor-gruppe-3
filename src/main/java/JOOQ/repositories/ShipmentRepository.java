package JOOQ.repositories;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.Checkpoint;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Parcel;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Shipment;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.records.ShipmentRecord;
import org.apache.commons.lang3.ObjectUtils;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.Tables.PARCEL;
import static no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.Tables.SHIPMENT;
import static no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.Checkpoint.CHECKPOINT;


@RequiredArgsConstructor
@Transactional
@Repository
public class ShipmentRepository implements JOOQRepository<Shipment>{

    private final DSLContext dslContext;

    @Override
    public Shipment save(Shipment shipment){
        ShipmentRecord shipmentRecord = dslContext.insertInto(SHIPMENT)
                .set(SHIPMENT.DELIVERED, shipment.getDelivered())
                .set(SHIPMENT.EXPECTED_DELIVERY_DATE, shipment.getExpectedDeliveryDate())
                .set(SHIPMENT.TIME_CREATED, shipment.getTimeCreated())
                .set(SHIPMENT.PAYER_ID, shipment.getPayerId())
                .set(SHIPMENT.RECEIVER_ID, shipment.getReceiverId())
                .set(SHIPMENT.SENDER_ID, shipment.getSenderId())
                .returning(SHIPMENT.SHIPMENT_ID).fetchOne();


        if (shipmentRecord != null) {
            shipment.setShipmentId(shipmentRecord.getShipmentId());
            return shipment;
        }
        return null;
    }

    @Override
    public Shipment update(Shipment shipment, long id) {
        ShipmentRecord shipmentRecord = (ShipmentRecord) dslContext.update(SHIPMENT)
                .set(SHIPMENT.DELIVERED, shipment.getDelivered())
                .set(SHIPMENT.EXPECTED_DELIVERY_DATE, shipment.getExpectedDeliveryDate())
                .set(SHIPMENT.TIME_CREATED, shipment.getTimeCreated())
                .set(SHIPMENT.PAYER_ID, shipment.getPayerId())
                .set(SHIPMENT.RECEIVER_ID, shipment.getReceiverId())
                .set(SHIPMENT.SENDER_ID, shipment.getSenderId())
                .where(SHIPMENT.SHIPMENT_ID.eq(id));

        return (shipmentRecord != null)  ? shipment : null;

    }

    @Override
    public List<Shipment> findAll() {
        return dslContext
                .selectFrom(SHIPMENT)
                .fetchInto(Shipment.class);
    }

    @Override
    public Optional<Shipment> findById(long id) {
        Shipment shipment = dslContext.selectFrom(SHIPMENT).where(SHIPMENT.SHIPMENT_ID.eq(id)).fetchOneInto(Shipment.class);
        return (ObjectUtils.isEmpty(shipment)) ? Optional.empty() : Optional.of(shipment);
    }

    public boolean deleteById(long id) {
        return dslContext.delete(SHIPMENT)
                .where(SHIPMENT.SHIPMENT_ID.eq(id))
                .execute() == 1;
    }



}
