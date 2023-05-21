package JOOQ.service;

import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.ValidPostalCodesDao;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.ValidPostalCodes;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;
import java.util.NoSuchElementException;

import static no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.Tables.VALID_POSTAL_CODES;

// Declares this class as a service and that it should be transactional
@Service("ValidPostalCodesServiceDAO")
@Transactional
public class ValidPostalCodeService {

    // The DSLContext provides methods for executing database queries
    private final DSLContext dslContext;

    // DAO to perform database operations related to ValidPostalCodes
    private final ValidPostalCodesDao dao;

    // Constructs a ValidPostalCodeService
    public ValidPostalCodeService(DSLContext dslContext, ValidPostalCodesDao dao) {
        this.dslContext = dslContext;
        this.dao = dao;
    }

    // Creates a new ValidPostalCodes record
    public ValidPostalCodes create(ValidPostalCodes validPostalCodes) {
        dao.insert(validPostalCodes);
        return validPostalCodes;
    }

    // Updates an existing ValidPostalCodes record
    public ValidPostalCodes update(ValidPostalCodes validPostalCodes) {
        dao.update(validPostalCodes);
        return validPostalCodes;
    }

    // Retrieves all ValidPostalCodes records
    public List<ValidPostalCodes> getAll() {
        return dao.findAll();
    }

    // Retrieves a specific ValidPostalCodes record by postal code
    public ValidPostalCodes getOne(String postalCode) {
        ValidPostalCodes validPostalCodes = dao.fetchOneByPostalCode(postalCode);
        if (validPostalCodes == null) {
            throw new NoSuchElementException(MessageFormat.format("Valid postal code {0} not found", postalCode));
        }
        return validPostalCodes;
    }

    // Retrieves a random postal code
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