package JOOQ.service;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.CheckpointDao;

import lombok.RequiredArgsConstructor;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Checkpoint;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Transactional
@Service(value = "CheckpointServiceDAO")
public class CheckpointServiceImpl implements CheckpointService {

    private final CheckpointDao CheckpointDao;

    @Override
    public Checkpoint create(Checkpoint Checkpoint) {
        CheckpointDao.insert(Checkpoint);

        return Checkpoint;
    }

    @Override
    public Checkpoint update(Checkpoint Checkpoint) {
        CheckpointDao.update(Checkpoint);
        return Checkpoint;
    }

    @Override
    public List<Checkpoint> getAll() {
        return CheckpointDao.findAll();
    }


    @Override
    public Checkpoint getOne(long id) {
        Checkpoint Checkpoint = CheckpointDao.findById(id);
        if(Checkpoint == null){
            throw new NoSuchElementException(MessageFormat.format("Checkpoint id {0} not found", String.valueOf(id)));
        }
        return Checkpoint;
    }

    @Override
    public void deleteById(long id) {
        CheckpointDao.deleteById(id);
    }
}



