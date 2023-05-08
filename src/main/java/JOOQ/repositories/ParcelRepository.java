package JOOQ.repositories;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Customer;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Parcel;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.records.CustomerRecord;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.records.ParcelRecord;
import org.apache.commons.lang3.ObjectUtils;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.Tables.CUSTOMER;
import static no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.Tables.PARCEL;


@RequiredArgsConstructor
@Transactional
@Repository
public class ParcelRepository implements JOOQRepository<Parcel>{

    private final DSLContext dslContext;

    @Override
    public Parcel save(Parcel Parcel){
        ParcelRecord ParcelRecord = dslContext.insertInto(PARCEL)
                .set(PARCEL.WEIGHT, Parcel.getWeight())
                .set(PARCEL.WEIGHT_CLASS, Parcel.getWeightClass())
                .returning(PARCEL.PARCEL_ID).fetchOne();


        if (ParcelRecord != null) {
            Parcel.setParcelId(ParcelRecord.getParcelId());
            return Parcel;
        }
        return null;
    }



    @Override
    public Parcel update(Parcel Parcel, long id) {
        ParcelRecord ParcelRecord = (ParcelRecord) dslContext.update(PARCEL)
                .set(PARCEL.WEIGHT, Parcel.getWeight())
                .set(PARCEL.WEIGHT_CLASS, Parcel.getWeightClass())
                .where(PARCEL.PARCEL_ID.eq(id));

        return (ParcelRecord != null)  ? Parcel : null;

    }

    @Override
    public List<Parcel> findAll() {
        return dslContext
                .selectFrom(PARCEL)
                .fetchInto(Parcel.class);
    }

    @Override
    public Optional<Parcel> findById(long id) {
        Parcel Parcel = dslContext.selectFrom(PARCEL).where(PARCEL.PARCEL_ID.eq(id)).fetchOneInto(Parcel.class);
        return (ObjectUtils.isEmpty(Parcel)) ? Optional.empty() : Optional.of(Parcel);
    }



    public boolean deleteById(long id) {
        return dslContext.delete(PARCEL)
                .where(PARCEL.PARCEL_ID.eq(id))
                .execute() == 1;
    }



}
