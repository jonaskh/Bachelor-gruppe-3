package JOOQ.repositories;

import java.util.List;
import java.util.Optional;

/**
 * A generic interface for JOOQ repositories.
 *
 * @param <T> - the type of the table POJO.
 */
public interface JOOQRepository<T> {

    /**
     * Saves a table POJO in the database.
     *
     * @param tablePojo - the table POJO to be saved.
     * @return T - the saved table POJO.
     */
    T save(T tablePojo);

    /**
     * Updates a table POJO in the database.
     *
     * @param tablePojo - the table POJO with updated values.
     * @param id - the ID of the table record to be updated.
     * @return T - the updated table POJO.
     */
    T update(T tablePojo, long id);

    /**
     * Retrieves all table POJOs from the database.
     *
     * @return List<T> - a list of table POJOs.
     */
    List<T> findAll();

    /**
     * Retrieves a table POJO from the database by ID.
     *
     * @param id - the ID of the table record.
     * @return Optional<T> - an optional table POJO.
     */
    Optional<T> findById(long id);

    /**
     * Deletes a table record from the database by ID.
     *
     * @param id - the ID of the table record to be deleted.
     * @return boolean - true if the deletion is successful, false otherwise.
     */
    boolean deleteById(long id);
}
