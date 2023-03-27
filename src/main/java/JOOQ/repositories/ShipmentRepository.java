package JOOQ.repositories;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Shipment;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.records.ShipmentRecord;
import org.apache.commons.lang3.ObjectUtils;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.Tables.SHIPMENT;


@RequiredArgsConstructor
@Transactional
@Repository
public class ShipmentRepository implements JOOQRepository<Shipment>{

    private final DSLContext dslContext;

    @Override
    public Shipment save(Shipment shipment){
        ShipmentRecord shipmentRecord = (ShipmentRecord) dslContext.insertInto(SHIPMENT)
                .set(SHIPMENT.CUSTOMER_ID, shipment.getCustomerId())
                .set(SHIPMENT.DELIVERED, shipment.getDelivered())
                .set(SHIPMENT.PAYER_ID, shipment.getPayerId())
                .set(SHIPMENT.RECEIVER_NAME, shipment.getReceiverName())
                .set(SHIPMENT.RECEIVING_ADDRESS, shipment.getReceivingAddress())
                .set(SHIPMENT.RECEIVING_ZIP, shipment.getReceivingZip())
                .set(SHIPMENT.SENDER_ID, shipment.getSenderId())
                .set(SHIPMENT.CHECKPOINT_ID, shipment.getCheckpointId())
                .returning(SHIPMENT.ORDER_ID).fetchOne();


        if (shipmentRecord != null) {
            shipment.setOrderId(shipmentRecord.getOrderId());
            return shipment;
        }
        return null;
    }

    @Override
    public Shipment update(Shipment shipment, int id) {
        ShipmentRecord shipmentRecord = (ShipmentRecord) dslContext.update(SHIPMENT)
                .set(SHIPMENT.CUSTOMER_ID, shipment.getCustomerId())
                .set(SHIPMENT.DELIVERED, shipment.getDelivered())
                .set(SHIPMENT.PAYER_ID, shipment.getPayerId())
                .set(SHIPMENT.RECEIVER_NAME, shipment.getReceiverName())
                .set(SHIPMENT.RECEIVING_ADDRESS, shipment.getReceivingAddress())
                .set(SHIPMENT.RECEIVING_ZIP, shipment.getReceivingZip())
                .set(SHIPMENT.SENDER_ID, shipment.getSenderId())
                .set(SHIPMENT.CHECKPOINT_ID, shipment.getCheckpointId())
                .where(SHIPMENT.ORDER_ID.eq(id));

        return (shipmentRecord != null)  ? shipment : null;

    }

    @Override
    public List<Shipment> findAll() {
        return dslContext
                .selectFrom(SHIPMENT)
                .fetchInto(Shipment.class);
    }

    @Override
    public Optional<Shipment> findById(int id) {
        Shipment shipment = dslContext.selectFrom(SHIPMENT).where(SHIPMENT.ORDER_ID.eq(id)).fetchOneInto(Shipment.class);
        return (ObjectUtils.isEmpty(shipment)) ? Optional.empty() : Optional.of(shipment);
    }

    public boolean deleteById(int id) {
        return dslContext.delete(SHIPMENT)
                .where(SHIPMENT.ORDER_ID.eq(id))
                .execute() == 1;
    }



}
