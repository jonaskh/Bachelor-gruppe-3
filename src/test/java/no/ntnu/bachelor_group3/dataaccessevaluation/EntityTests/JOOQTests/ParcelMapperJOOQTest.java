package no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.JOOQTests;

import JOOQ.Data.ParcelMapper;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Parcel;
import org.jooq.meta.derby.sys.Sys;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParcelMapperJOOQTest {

    @Test
    public void testMap() {
        Parcel jooqParcel = new Parcel();
        jooqParcel.setShipmentId(2L);
        jooqParcel.setWeight(3.0);
        jooqParcel.setWeightClass(1);

        JOOQ.Data.Parcel customParcel = ParcelMapper.map(jooqParcel);

        assertEquals(jooqParcel.getParcelId(), customParcel.getParcelId());
        assertEquals(jooqParcel.getShipmentId(), customParcel.getShipmentId());
        assertEquals(jooqParcel.getWeight(), customParcel.getWeight());
        assertEquals(jooqParcel.getWeightClass(), customParcel.getWeightClass());

        System.out.println(jooqParcel);
        System.out.println(customParcel.toString());
    }

    @Test
    public void testMapToJooqParcel() {
        JOOQ.Data.Parcel customParcel = new JOOQ.Data.Parcel();
        customParcel.setShipmentId(4L);
        customParcel.setWeight(6.0);
        customParcel.setWeightClass(2);

        Parcel jooqParcel = ParcelMapper.mapToJooqParcel(customParcel);

        assertEquals(customParcel.getParcelId(), jooqParcel.getParcelId());
        assertEquals(customParcel.getShipmentId(), jooqParcel.getShipmentId());
        assertEquals(customParcel.getWeight(), jooqParcel.getWeight());
        assertEquals(customParcel.getWeightClass(), jooqParcel.getWeightClass());

        System.out.println(jooqParcel);
        System.out.println(customParcel.toString());
    }
}
