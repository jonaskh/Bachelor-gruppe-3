package JOOQ.repositories;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Customer;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.records.CustomerRecord;
import org.apache.commons.lang3.ObjectUtils;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.Tables.CUSTOMER;


@RequiredArgsConstructor
@Transactional
@Repository
public class CustomerRepository implements JOOQRepository<Customer>{

    private final DSLContext dslContext;

    @Override
    public Customer save(Customer Customer){
        CustomerRecord CustomerRecord = dslContext.insertInto(CUSTOMER)
                .set(CUSTOMER.ADDRESS, Customer.getAddress())
                .set(CUSTOMER.NAME, Customer.getName())
                .set(CUSTOMER.ZIP_CODE, Customer.getZipCode())
                .returning(CUSTOMER.CUSTOMER_ID).fetchOne();


        if (CustomerRecord != null) {
            Customer.setCustomerId(CustomerRecord.getCustomerId());
            return Customer;
        }
        return null;
    }



    @Override
    public Customer update(Customer Customer, long id) {
        CustomerRecord CustomerRecord = (CustomerRecord) dslContext.update(CUSTOMER)
                .set(CUSTOMER.ADDRESS, Customer.getAddress())
                .set(CUSTOMER.NAME, Customer.getName())
                .set(CUSTOMER.ZIP_CODE, Customer.getZipCode())
                .where(CUSTOMER.CUSTOMER_ID.eq(id));

        return (CustomerRecord != null)  ? Customer : null;

    }

    @Override
    public List<Customer> findAll() {
        return dslContext
                .selectFrom(CUSTOMER)
                .fetchInto(Customer.class);
    }

    @Override
    public Optional<Customer> findById(long id) {
        Customer Customer = dslContext.selectFrom(CUSTOMER).where(CUSTOMER.CUSTOMER_ID.eq(id)).fetchOneInto(Customer.class);
        return (ObjectUtils.isEmpty(Customer)) ? Optional.empty() : Optional.of(Customer);
    }

    public boolean deleteById(long id) {
        return dslContext.delete(CUSTOMER)
                .where(CUSTOMER.CUSTOMER_ID.eq(id))
                .execute() == 1;
    }



}
