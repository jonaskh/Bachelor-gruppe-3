package no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.JOOQTests;


import JOOQ.repositories.ValidPostalCodesRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.ValidPostalCodes;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.Tables.VALID_POSTAL_CODES;

public class ValidPostalCodeJOOQTest {

    private DSLContext dslContext;

    @BeforeEach
    void setUp() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");
        dslContext = DSL.using(conn, SQLDialect.POSTGRES);
    }

    @Test
    void testSave() {
        ValidPostalCodesRepository validPostalCodesRepository = new ValidPostalCodesRepository(dslContext);
        ValidPostalCodes validPostalCodes = new ValidPostalCodes().setPostalCode("1234").setCounty("Oslo").setMunicipality("Oslo");
        ValidPostalCodes savedValidPostalCodes = validPostalCodesRepository.save(validPostalCodes);
        assertThat(savedValidPostalCodes).isNotNull();
        assertThat(savedValidPostalCodes.getPostalCode()).isEqualTo("1234");
    }

    @Test
    void testFindAll() {
        ValidPostalCodesRepository validPostalCodesRepository = new ValidPostalCodesRepository(dslContext);
        List<ValidPostalCodes> validPostalCodesList = validPostalCodesRepository.findAll();
        assertThat(validPostalCodesList).isNotEmpty();
        System.out.println("Found " + validPostalCodesList.size() + " valid postal codes");

    }

    @Test
    void testFindById() {
        ValidPostalCodesRepository validPostalCodesRepository = new ValidPostalCodesRepository(dslContext);
        Optional<ValidPostalCodes> optionalValidPostalCodes = validPostalCodesRepository.findById(1);
        assertThat(optionalValidPostalCodes).isPresent();
        ValidPostalCodes validPostalCodes = optionalValidPostalCodes.get();
        assertThat(validPostalCodes.getPostalCode()).isEqualTo("0001");
        assertThat(validPostalCodes.getCounty()).isEqualTo("Oslo");
        assertThat(validPostalCodes.getMunicipality()).isEqualTo("Oslo");
    }

    @Test
    void testUpdate() {
        ValidPostalCodesRepository validPostalCodesRepository = new ValidPostalCodesRepository(dslContext);
        Optional<ValidPostalCodes> optionalValidPostalCodes = validPostalCodesRepository.findById(1);
        assertThat(optionalValidPostalCodes).isPresent();
        ValidPostalCodes validPostalCodes = optionalValidPostalCodes.get();
        validPostalCodes.setCounty("Viken");
        ValidPostalCodes updatedValidPostalCodes = validPostalCodesRepository.update(validPostalCodes, 1);
        assertThat(updatedValidPostalCodes).isNotNull();
        assertThat(updatedValidPostalCodes.getCounty()).isEqualTo("Viken");
    }

    @Test
    void testDeleteById() {
        ValidPostalCodesRepository validPostalCodesRepository = new ValidPostalCodesRepository(dslContext);
        boolean deleted = validPostalCodesRepository.deleteById(1);
        assertThat(deleted).isTrue();
    }
}
