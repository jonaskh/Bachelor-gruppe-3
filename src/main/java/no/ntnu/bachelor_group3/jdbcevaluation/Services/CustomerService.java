package no.ntnu.bachelor_group3.jdbcevaluation.Services;

import no.ntnu.bachelor_group3.jdbcevaluation.Data.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.Instant;

public class CustomerService {

    private static final String GET_CUSTOMER_BY_ID_QUERY = "SELECT * FROM customer WHERE customer_id = ?";
    private static final String INSERT_CUSTOMER_QUERY = "INSERT INTO customer (name, address, zip_code) VALUES (?, ?, ?)";
    private static final String UPDATE_CUSTOMER_QUERY = "UPDATE customer SET name = ?, address = ?, zip_code = ? WHERE customer_id = ?";
    private static final String DELETE_CUSTOMER_QUERY = "DELETE FROM customer WHERE customer_id = ?";

    public CustomerService() {}

    /**
     * Returns a customer from the customer table if it exists
     *
     * @param customerId the id of the customer to find
     * @param conn the connection to the database
     * @return a customer, or null if it does not exist
     * @throws SQLException
     */
    public Customer getCustomerById(Long customerId, Connection conn) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(GET_CUSTOMER_BY_ID_QUERY)) {
            stmt.setLong(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Customer(rs.getLong("customer_id"), rs.getString("name"),
                            rs.getString("address"), rs.getString("zip_code"));
                }
            }
        }
        return null;
    }

    public Long save(Customer customer, Connection conn) throws SQLException {

        if (customerExists(customer.getId(), conn)) {
            update(customer, conn);

        } else {
            return insert(customer, conn);
        }
        return null;
    }

    private void update(Customer customer, Connection conn) throws SQLException {
        try (PreparedStatement stmt = createUpdateStatement(customer, conn)) {
            var startTime = Instant.now();
            stmt.executeUpdate();
            var executionTime = Duration.between(startTime, Instant.now()).toNanos();
            System.out.println(executionTime);
        }
    }

    private Long insert(Customer customer, Connection conn) throws SQLException {
        Long id = 0l;
        try (PreparedStatement stmt = createInsertStatement(customer, conn)) {
            var startTime = Instant.now();
            int rowsAffected = stmt.executeUpdate();
            var executionTime = Duration.between(startTime, Instant.now()).toNanos();
            System.out.println(executionTime);

            if (rowsAffected > 0) {
                // Run a separate query to get the last inserted ID
                try (Statement stmt2 = conn.createStatement();
                     ResultSet rs = stmt2.executeQuery("SELECT DBINFO('sqlca.sqlerrd1') FROM customer")) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (Exception e) {
                    System.out.println("Could not get id of inserted customer");
                    e.printStackTrace();
                }
            }
        }
        return id;
    }

    private boolean customerExists(Long id, Connection conn) throws SQLException {
        if (id == null || id == 0) {
            return false;
        }

        try (PreparedStatement stmt = conn.prepareStatement(GET_CUSTOMER_BY_ID_QUERY)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void delete(Customer customer, Connection conn) throws SQLException {
        Long id = customer.getId();
        if (id > 0) {
            try (PreparedStatement stmt = conn.prepareStatement(DELETE_CUSTOMER_QUERY)) {
                stmt.setLong(1, id);
                stmt.executeUpdate();
                customer.setId(0L);
            }
        }
    }

    private PreparedStatement createInsertStatement(Customer customer, Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(INSERT_CUSTOMER_QUERY);
        stmt.setString(1, customer.getName());
        stmt.setString(2, customer.getAddress());
        stmt.setString(3, customer.getZipCode());
        return stmt;
    }

    private PreparedStatement createUpdateStatement(Customer customer, Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(UPDATE_CUSTOMER_QUERY);
        stmt.setString(1, customer.getName());
        stmt.setString(2, customer.getAddress());
        stmt.setString(3, customer.getZipCode());
        stmt.setLong(4, customer.getId());
        return stmt;
    }

}
