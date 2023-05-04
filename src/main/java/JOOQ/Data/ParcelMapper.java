//package JOOQ.Data;
//
//
//
//public class ParcelMapper {
//    public static JOOQ.Data.Parcel map(no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Parcel jooqParcel) {
//        JOOQ.Data.Parcel parcel = new JOOQ.Data.Parcel();
//        parcel.setParcelId(jooqParcel.getParcelId());
//        parcel.setShipmentId(jooqParcel.getShipmentId());
//        parcel.setWeight(jooqParcel.getWeight());
//        parcel.setWeightClass(jooqParcel.getWeightClass());
//        return parcel;
//    }
//
//    public static no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Parcel mapToJooqParcel(JOOQ.Data.Parcel customParcel) {
//        no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Parcel jooqParcel = new no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Parcel();
//        jooqParcel.setParcelId(customParcel.getParcelId());
//        jooqParcel.setShipmentId(customParcel.getShipmentId());
//        jooqParcel.setWeight(customParcel.getWeight());
//        jooqParcel.setWeightClass(customParcel.getWeightClass());
//        return jooqParcel;
//    }
//}
