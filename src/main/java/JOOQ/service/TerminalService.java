package JOOQ.service;

import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.Tables;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Terminal;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TerminalService {
    Terminal create(Terminal terminal);

    Terminal update(Terminal terminal);

    List<Terminal> getAll();

     Terminal getOne(long id);

    void deleteById(long id);
}
