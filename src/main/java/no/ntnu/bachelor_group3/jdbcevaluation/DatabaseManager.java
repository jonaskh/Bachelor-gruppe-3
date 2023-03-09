package no.ntnu.bachelor_group3.jdbcevaluation;

import no.ntnu.bachelor_group3.jdbcevaluation.Data.Customer;
import no.ntnu.bachelor_group3.jdbcevaluation.Data.Parcel;
import no.ntnu.bachelor_group3.jdbcevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.jdbcevaluation.Services.CustomerService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager implements AutoCloseable {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";
    private CustomerService customerService;

    private Connection conn;

    public DatabaseManager() throws SQLException {
        customerService = new CustomerService();
        conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        conn.setAutoCommit(false); // Start a new transaction
    }

    public void commit() throws SQLException {
        conn.commit();
    }

    public void rollback() throws SQLException {
        conn.rollback();
    }

    public void close() throws SQLException {
        conn.close();
    }

    public Customer getCustomerById(int customerId) throws SQLException {
        return customerService.getCustomerById(customerId, conn);
    }

    public void saveCustomer(Customer customer) throws SQLException {
        customerService.save(customer, conn);
    }

    public void deleteCustomer(Customer customer) throws SQLException {
        customerService.delete(customer, conn);
    }

    public Shipment getShipmentById(int shipmentId) throws SQLException {
        return Shipment.getShipmentById(shipmentId, customerService, conn);
    }

    public void saveShipment(Shipment shipment) throws SQLException {
        shipment.save(conn);
    }

    public void deleteShipment(Shipment shipment) throws SQLException {
        shipment.delete(conn);
    }

    public Parcel getParcelById(Long parcelId) throws SQLException {
        return Parcel.getParcelById(parcelId, customerService, conn);
    }

    public void saveParcel(Parcel parcel) throws SQLException {
        parcel.save(conn);
    }

    public void deleteParcel(Parcel parcel) throws SQLException {
        parcel.delete(conn);
    }

    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();

        String query = "SELECT * FROM Customer";
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Long id = rs.getLong("customerid");
                String name = rs.getString("name");
                String address = rs.getString("address");
                String zipCode = rs.getString("zip_code");

                Customer customer = new Customer(id, name, address, zipCode);
                customers.add(customer);
            }
        }

        return customers;
    }

    public List<Shipment> getAllShipments() throws SQLException {
        List<Shipment> shipments = new ArrayList<>();

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM shipment");
        while (rs.next()) {
            Customer sender = getCustomerById(rs.getInt("sender_id"));
            String address = rs.getString("receiving_address");
            String zip = rs.getString("receiving_zip");
            String name = rs.getString("receiver_name");
            Customer payer = getCustomerById(rs.getInt("payer_id"));

            Shipment shipment = new Shipment(
                    rs.getLong("id"),
                    sender,
                    address,
                    zip,
                    name,
                    payer,
                    rs.getDouble("total_cost")
            );

            shipments.add(shipment);
        }


        return shipments;
    }


    public List<Parcel> getParcelsForShipment(Shipment shipment) throws SQLException {
        List<Parcel> parcels = new ArrayList<>();

        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM parcel WHERE shipment_id = ?");
        stmt.setLong(1, shipment.getId());
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Parcel parcel = new Parcel(
                        rs.getLong("id"),
                        rs.getDouble("weight")
                );
                parcel.setShipment(shipment);
                parcels.add(parcel);
            }
        }


        return parcels;
    }

    public void execute(String sql) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
    }

}