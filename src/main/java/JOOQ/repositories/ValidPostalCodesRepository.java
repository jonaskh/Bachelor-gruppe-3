package JOOQ.repositories;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.TerminalId;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.ValidPostalCodes;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.Tables.TERMINAL_ID;
import static no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.Tables.VALID_POSTAL_CODES;

@RequiredArgsConstructor
@Transactional
@Repository
public class ValidPostalCodesRepository implements JOOQRepository<ValidPostalCodes> {

    private final DSLContext dslContext;

    @Override
    public ValidPostalCodes save(ValidPostalCodes validPostalCodes) {
        dslContext.insertInto(VALID_POSTAL_CODES)
                .set(VALID_POSTAL_CODES.POSTAL_CODE, validPostalCodes.getPostalCode())
                .set(VALID_POSTAL_CODES.COUNTY, validPostalCodes.getCounty())
                .set(VALID_POSTAL_CODES.MUNICIPALITY, validPostalCodes.getMunicipality())
                .execute();

        return validPostalCodes;
    }

    @Override
    public ValidPostalCodes update(ValidPostalCodes validPostalCodes, long id) {
        dslContext.update(VALID_POSTAL_CODES)
                .set(VALID_POSTAL_CODES.POSTAL_CODE, validPostalCodes.getPostalCode())
                .set(VALID_POSTAL_CODES.COUNTY, validPostalCodes.getCounty())
                .set(VALID_POSTAL_CODES.MUNICIPALITY, validPostalCodes.getMunicipality())
                .where(VALID_POSTAL_CODES.POSTAL_CODE.eq(validPostalCodes.getPostalCode()))
                .execute();

        return validPostalCodes;
    }


    public ValidPostalCodes update(ValidPostalCodes validPostalCodes, String id) {
        dslContext.update(VALID_POSTAL_CODES)
                .set(VALID_POSTAL_CODES.POSTAL_CODE, validPostalCodes.getPostalCode())
                .set(VALID_POSTAL_CODES.COUNTY, validPostalCodes.getCounty())
                .set(VALID_POSTAL_CODES.MUNICIPALITY, validPostalCodes.getMunicipality())
                .where(VALID_POSTAL_CODES.POSTAL_CODE.eq(validPostalCodes.getPostalCode()))
                .execute();

        return validPostalCodes;
    }

    @Override
    public List<ValidPostalCodes> findAll() {
        return dslContext
                .selectFrom(VALID_POSTAL_CODES)
                .fetchInto(ValidPostalCodes.class);
    }

    @Override
    public Optional<ValidPostalCodes> findById(long id) {
        ValidPostalCodes validPostalCodes = dslContext.selectFrom(VALID_POSTAL_CODES)
                .where(VALID_POSTAL_CODES.POSTAL_CODE.eq(String.valueOf((int) id)))
                .fetchOneInto(ValidPostalCodes.class);
        return (validPostalCodes == null) ? Optional.empty() : Optional.of(validPostalCodes);
    }


    public Optional<ValidPostalCodes> findById(String id) {
        ValidPostalCodes validPostalCodes = dslContext.selectFrom(VALID_POSTAL_CODES)
                .where(VALID_POSTAL_CODES.POSTAL_CODE.eq(id))
                .fetchOneInto(ValidPostalCodes.class);
        return (validPostalCodes == null) ? Optional.empty() : Optional.of(validPostalCodes);
    }



    @Override
    public boolean deleteById(long id) {
        return dslContext.delete(VALID_POSTAL_CODES)
                .where(VALID_POSTAL_CODES.POSTAL_CODE.eq(String.valueOf((int) id)))
                .execute() == 1;
    }

    //For postnummer med 0 på starten
    public boolean deleteById(String id) {
        return dslContext.delete(VALID_POSTAL_CODES)
                .where(VALID_POSTAL_CODES.POSTAL_CODE.eq(id))
                .execute() == 1;
    }

    public String getRandomZipCode() {
        Result<Record1<String>> result = dslContext.select(VALID_POSTAL_CODES.POSTAL_CODE)
                .from(VALID_POSTAL_CODES)
                .orderBy(DSL.rand())
                .limit(1)
                .fetch();

        if (result.isEmpty()) {
            throw new IllegalStateException("No valid postal codes found");
        }

        return result.get(0).value1();
    }

}
