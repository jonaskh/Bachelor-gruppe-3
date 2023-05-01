package JOOQ.Data;

import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Parcel;


public class ParcelMapper {
    public static JOOQ.Data.Parcel map(JOOQ.Data.Parcel parcel) {
        return new JOOQ.Data.Parcel()
                .setParcelId(parcel.getParcelId())
                .setShipmentId(parcel.getShipmentId())
                .setWeight(parcel.getWeight())
                .setWeightClass(parcel.getWeightClass());

    }

    public static no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Parcel mapToJooqParcel(Parcel parcel) {
        return new no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Parcel()
                .setParcelId(parcel.getParcelId())
                .setWeight(parcel.getWeight())
                .setShipmentId(parcel.getShipmentId())
                .setWeightClass(parcel.getWeightClass());
    }
}
