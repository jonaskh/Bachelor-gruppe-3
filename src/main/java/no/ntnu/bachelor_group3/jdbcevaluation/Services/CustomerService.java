package no.ntnu.bachelor_group3.jdbcevaluation.Services;

import no.ntnu.bachelor_group3.jdbcevaluation.Data.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
    public Customer getCustomerById(int customerId, Connection conn) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(GET_CUSTOMER_BY_ID_QUERY)) {
            stmt.setInt(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Customer(rs.getLong("customer_id"), rs.getString("name"),
                            rs.getString("address"), rs.getString("zip_code"));
                }
            }
        }
        return null;
    }

    /**
     * Saves a customer to the database or updates an existing one
     *
     * @param customer the customer to insert or update
     * @param conn the connection to the database
     * @throws SQLException
     */
    public void save(Customer customer, Connection conn) throws SQLException {
        if (customerExists(customer.getId(), conn)) {
            update(customer, conn);
        } else {
            insert(customer, conn);
        }
    }

    /**
     * Updates a customer in the database
     *
     * @param customer the customer to update
     * @param conn the connection to the database
     * @throws SQLException
     */
    private void update(Customer customer, Connection conn) throws SQLException {
        try (PreparedStatement stmt = createUpdateStatement(customer, conn)) {
            stmt.executeUpdate();
        }
    }

    /**
     * Inserts a customer into the customer table
     *
     * @param customer the customer to insert
     * @param conn the connection to the database
     * @throws SQLException
     */
    private void insert(Customer customer, Connection conn) throws SQLException {
        try (PreparedStatement stmt = createInsertStatement(customer, conn)) {
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        customer.setId(rs.getLong(1));
                    }
                }
            }
        }
    }

    /**
     * Checks if the customer exists in the customer table
     *
     * @param id the id of the customer
     * @param conn the connection to the database
     * @return true if the customer exists
     * @throws SQLException
     */
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


    /**
     * Deletes a customer from the customer table if it exists
     *
     * @param customer the customer to delete
     * @param conn the connection to the database
     * @throws SQLException
     */
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
        PreparedStatement stmt = conn.prepareStatement(INSERT_CUSTOMER_QUERY, Statement.RETURN_GENERATED_KEYS);
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
