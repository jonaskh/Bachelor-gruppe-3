//package no.ntnu.bachelor_group3.jdbcevaluation.Data;
//
//import java.sql.*;
//
//public class Customer {
//    private Long id;
//    private String name;
//    private String address;
//    private String zipCode;
//
//    // Constructor
//    public Customer(Long id, String name, String address, String zipCode) {
//        this.id = id;
//        this.name = name;
//        this.address = address;
//        this.zipCode = zipCode;
//    }
//
//    // Getters and setters for each field
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public String getZipCode() {
//        return zipCode;
//    }
//
//    public void setZipCode(String zipCode) {
//        this.zipCode = zipCode;
//    }
//
//    // Static method to retrieve a customer from the database given an ID
//    public static Customer getCustomerById(int customerId, Connection conn) throws SQLException {
//        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM customer WHERE id = ?");
//        stmt.setInt(1, customerId);
//        ResultSet rs = stmt.executeQuery();
//        if (rs.next()) {
//            Customer customer = new Customer(rs.getLong("id"), rs.getString("name"),
//                    rs.getString("address"), rs.getString("zip_code"));
//            return customer;
//        }
//        return null;
//    }
//
//    // Instance method to save this customer to the database
//    public void save(Connection conn) throws SQLException {
//        PreparedStatement stmt;
//        if (id == 0) {
//            // This is a new customer, so insert it into the database
//            stmt = conn.prepareStatement("INSERT INTO customer (name, address, zip_code) VALUES (?, ?, ?)",
//                    Statement.RETURN_GENERATED_KEYS);
//            stmt.setString(1, name);
//            stmt.setString(2, address);
//            stmt.setString(3, zipCode);
//            int rowsInserted = stmt.executeUpdate();
//            if (rowsInserted > 0) {
//                ResultSet rs = stmt.getGeneratedKeys();
//                if (rs.next()) {
//                    id = rs.getLong(1);
//                }
//            }
//        } else {
//            // This is an existing customer, so update it in the database
//            stmt = conn.prepareStatement("UPDATE customer SET name = ?, address = ?, zip_code = ?, email = ? WHERE id = ?");
//            stmt.setString(1, name);
//            stmt.setString(2, address);
//            stmt.setString(3, zipCode);
//            stmt.setLong(5, id);
//            stmt.executeUpdate();
//        }
//    }
//
//    // Instance method to delete this customer from the database
//    public void delete(Connection conn) throws SQLException {
//        if (id > 0) {
//            PreparedStatement stmt = conn.prepareStatement("DELETE FROM customer WHERE id = ?");
//            stmt.setLong(1, id);
//            stmt.executeUpdate();
//            id = 0L;
//        }
//    }
//}