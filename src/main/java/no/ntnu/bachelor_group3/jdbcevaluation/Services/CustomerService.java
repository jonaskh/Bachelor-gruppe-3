package no.ntnu.bachelor_group3.jdbcevaluation.Services;

import no.ntnu.bachelor_group3.jdbcevaluation.Data.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerService {

    public CustomerService() {}

    public Customer getCustomerById(int customerId, Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM customer WHERE customer_id = ?");
        stmt.setInt(1, customerId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Customer customer = new Customer(rs.getLong("customer_id"), rs.getString("name"),
                    rs.getString("address"), rs.getString("zip_code"));
            return customer;
        }
        return null;
    }

    public void save(Customer customer, Connection conn) throws SQLException {
        PreparedStatement stmt;
        Long id = customer.getId();
        if (id == 0) {
            // This is a new customer, so insert it into the database
            stmt = conn.prepareStatement("INSERT INTO customer (name, address, zip_code) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getAddress());
            stmt.setString(3, customer.getZipCode());
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getLong(1);
                }
            }
        } else {
            // This is an existing customer, so update it in the database
            stmt = conn.prepareStatement("UPDATE customer SET name = ?, address = ?, zip_code = ?, email = ? WHERE customer_id = ?");
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getAddress());
            stmt.setString(3, customer.getZipCode());
            stmt.setLong(5, id);
            stmt.executeUpdate();
        }
    }

    public void delete(Customer customer, Connection conn) throws SQLException {
        Long id = customer.getId();
        if (id > 0) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM customer WHERE customer_id = ?");
            stmt.setLong(1, id);
            stmt.executeUpdate();
            id = 0L;
        }
    }
}
