package JOOQ.repositories;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Customer;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.records.CustomerRecord;
import org.apache.commons.lang3.ObjectUtils;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.Tables.CUSTOMER;

@RequiredArgsConstructor
@Transactional
@Repository
public class CustomerRepository implements JOOQRepository<Customer>{

    private final DSLContext dslContext;

    /**
     * Saves a Customer record in the database.
     *
     * @param customer - the Customer object to be saved.
     * @return Customer - the saved Customer object.
     */
    @Override
    public Customer save(Customer customer){
        CustomerRecord customerRecord = dslContext.insertInto(CUSTOMER)
                .set(CUSTOMER.ADDRESS, customer.getAddress())
                .set(CUSTOMER.NAME, customer.getName())
                .set(CUSTOMER.ZIP_CODE, customer.getZipCode())
                .returning(CUSTOMER.CUSTOMER_ID).fetchOne();

        if (customerRecord != null) {
            customer.setCustomerId(customerRecord.getCustomerId());
            return customer;
        }
        return null;
    }

    /**
     * Updates a Customer record in the database.
     *
     * @param customer - the Customer object with updated values.
     * @param id - the ID of the Customer record to be updated.
     * @return Customer - the updated Customer object.
     */
    @Override
    public Customer update(Customer customer, long id) {
        CustomerRecord customerRecord = (CustomerRecord) dslContext.update(CUSTOMER)
                .set(CUSTOMER.ADDRESS, customer.getAddress())
                .set(CUSTOMER.NAME, customer.getName())
                .set(CUSTOMER.ZIP_CODE, customer.getZipCode())
                .where(CUSTOMER.CUSTOMER_ID.eq(id));

        return (customerRecord != null) ? customer : null;
    }

    /**
     * Retrieves all Customer records from the database.
     *
     * @return List<Customer> - a list of Customer objects.
     */
    @Override
    public List<Customer> findAll() {
        return dslContext
                .selectFrom(CUSTOMER)
                .fetchInto(Customer.class);
    }

    /**
     * Retrieves a Customer record from the database by ID.
     *
     * @param id - the ID of the Customer record.
     * @return Optional<Customer> - an optional Customer object.
     */
    @Override
    public Optional<Customer> findById(long id) {
        Customer customer = dslContext.selectFrom(CUSTOMER).where(CUSTOMER.CUSTOMER_ID.eq(id)).fetchOneInto(Customer.class);
        return (ObjectUtils.isEmpty(customer)) ? Optional.empty() : Optional.of(customer);
    }

    /**
     * Deletes a Customer record from the database by ID.
     *
     * @param id - the ID of the Customer record to be deleted.
     * @return boolean - true if the deletion is successful, false otherwise.
     */
    public boolean deleteById(long id) {
        return dslContext.delete(CUSTOMER)
                .where(CUSTOMER.CUSTOMER_ID.eq(id))
                .execute() == 1;
    }

/**
 * Retrieves the next available customer ID in the database
