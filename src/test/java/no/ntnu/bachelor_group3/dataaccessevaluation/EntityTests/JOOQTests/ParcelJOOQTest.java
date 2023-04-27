//package no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.JOOQTests;
//
//import JOOQ.repositories.CustomerRepository;
//import JOOQ.repositories.ParcelRepository;
//import JOOQ.service.CustomerService;
//import JOOQ.service.ParcelService;
//import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.CustomerDao;
//import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.ParcelDao;
//import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Customer;
//import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Parcel;
//import org.jooq.DSLContext;
//import org.jooq.SQLDialect;
//import org.jooq.impl.DSL;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//public class ParcelJOOQTest {
//
//    private DSLContext dslContext;
//
//
//    @BeforeEach
//    void setUp() throws SQLException {
//        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");
//        dslContext = DSL.using(conn, SQLDialect.POSTGRES);
//    }
//
//    @Test
//    public void testFindAll() {
//        ParcelRepository repository = new ParcelRepository(dslContext);
//        List<Parcel> Parcels = repository.findAll();
//        assertNotNull(Parcels);
//        assertFalse(Parcels.isEmpty());
//    }
//
//    @Test
//    public void testCreateParcel() {
//        Parcel Parcel = new Parcel()
//                .setWeight(200.0)
//                .setWeightClass(1);
//        ParcelService service = new ParcelService(new ParcelDao(dslContext.configuration()));
//        Parcel savedParcel = service.create(Parcel);
//        assertNotNull(savedParcel.getParcelId());
//    }
//}
