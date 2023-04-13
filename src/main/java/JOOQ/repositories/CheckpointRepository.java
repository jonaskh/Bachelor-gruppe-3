package JOOQ.repositories;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Checkpoint;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.records.CheckpointRecord;
import org.apache.commons.lang3.ObjectUtils;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.Tables.CHECKPOINT;


@RequiredArgsConstructor
@Transactional
@Repository
public class CheckpointRepository implements JOOQRepository<Checkpoint>{

    private final DSLContext dslContext;

    @Override
    public Checkpoint save(Checkpoint Checkpoint){
        CheckpointRecord CheckpointRecord = (CheckpointRecord) dslContext.insertInto(CHECKPOINT)
                .set(CHECKPOINT.COST, Checkpoint.getCost())
                .set(CHECKPOINT.TIME, Checkpoint.getTime())
                .set(CHECKPOINT.TYPE, Checkpoint.getType())
                .set(CHECKPOINT.TERMINAL_ID, Checkpoint.getTerminalId())
                .returning(CHECKPOINT.CHECKPOINT_ID).fetchOne();


        if (CheckpointRecord != null) {
            Checkpoint.setCheckpointId(CheckpointRecord.getCheckpointId());
            return Checkpoint;
        }
        return null;
    }

    @Override
    public Checkpoint update(Checkpoint Checkpoint, long id) {
        CheckpointRecord CheckpointRecord = (CheckpointRecord) dslContext.update(CHECKPOINT)
                .set(CHECKPOINT.COST, Checkpoint.getCost())
                .set(CHECKPOINT.TIME, Checkpoint.getTime())
                .set(CHECKPOINT.TYPE, Checkpoint.getType())
                .set(CHECKPOINT.TERMINAL_ID, Checkpoint.getTerminalId())
                .where(CHECKPOINT.CHECKPOINT_ID.eq(id));

        return (CheckpointRecord != null)  ? Checkpoint : null;

    }

    @Override
    public List<Checkpoint> findAll() {
        return dslContext
                .selectFrom(CHECKPOINT)
                .fetchInto(Checkpoint.class);
    }

    @Override
    public Optional<Checkpoint> findById(long id) {
        Checkpoint Checkpoint = dslContext.selectFrom(CHECKPOINT).where(CHECKPOINT.CHECKPOINT_ID.eq(id)).fetchOneInto(Checkpoint.class);
        return (ObjectUtils.isEmpty(Checkpoint)) ? Optional.empty() : Optional.of(Checkpoint);
    }

    public boolean deleteById(long id) {
        return dslContext.delete(CHECKPOINT)
                .where(CHECKPOINT.CHECKPOINT_ID.eq(id))
                .execute() == 1;
    }



}
