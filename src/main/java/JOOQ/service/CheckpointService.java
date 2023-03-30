package JOOQ.service;

import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.Tables;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Checkpoint;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CheckpointService {
    Checkpoint create(Checkpoint Checkpoint);

    Checkpoint update(Checkpoint Checkpoint);

    List<Checkpoint> getAll();

    Checkpoint getOne(int id);

    void deleteById(int id);
}
