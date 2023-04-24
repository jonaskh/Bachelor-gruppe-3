package JOOQ.service;

import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.ValidPostalCodesDao;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.ValidPostalCodes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;
import java.util.NoSuchElementException;

@Service("ValidPostalCodesServiceDAO")
@Transactional
public class ValidPostalCodeService {

    private final ValidPostalCodesDao dao;

    public ValidPostalCodeService(ValidPostalCodesDao dao) {
        this.dao = dao;
    }

    public ValidPostalCodes create(ValidPostalCodes validPostalCodes) {
        dao.insert(validPostalCodes);
        return validPostalCodes;
    }

    public ValidPostalCodes update(ValidPostalCodes validPostalCodes) {
        dao.update(validPostalCodes);
        return validPostalCodes;
    }

    public List<ValidPostalCodes> getAll() {
        return dao.findAll();
    }

    public ValidPostalCodes getOne(String postalCode) {
        ValidPostalCodes validPostalCodes = dao.fetchOneByPostalCode(postalCode);
        if (validPostalCodes == null) {
            throw new NoSuchElementException(MessageFormat.format("Valid postal code {0} not found", postalCode));
        }
        return validPostalCodes;
    }


}
