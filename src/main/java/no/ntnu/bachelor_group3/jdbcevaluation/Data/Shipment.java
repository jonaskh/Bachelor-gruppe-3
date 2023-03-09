package no.ntnu.bachelor_group3.jdbcevaluation.Data;

import no.ntnu.bachelor_group3.jdbcevaluation.Services.CustomerService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Shipment {
    private Long id;
    private Customer sender;

    private String receiving_address;
    private String receiving_zip;
    private String receiver_name;
    private Customer payer;
    private double totalCost;
    private List<Parcel> parcels;

    // Constructor
    public Shipment(Long id, Customer sender, String receiving_address, String receiving_zip, String receiver_name,
                    Customer payer, double totalCost) {
        this.id = id;
        this.sender = sender;
        this.receiving_address = receiving_address;
        this.receiving_zip = receiving_zip;
        this.receiver_name = receiver_name;
        this.payer = payer;
        this.totalCost = totalCost;
        this.parcels = new ArrayList<>();
    }

    // Getters and setters for each field
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getSender() {
        return sender;
    }

    public void setSender(Customer sender) {
        this.sender = sender;
    }

    public String getReceiving_address() {
        return receiving_address;
    }

    public void setReceiving_address(String receiving_address) {
        this.receiving_address = receiving_address;
    }

    public String getReceiving_zip() {
        return receiving_zip;
    }

    public void setReceiving_zip(String receiving_zip) {
        this.receiving_zip = receiving_zip;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public Customer getPayer() {
        return payer;
    }

    public void setPayer(Customer payer) {
        this.payer = payer;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public List<Parcel> getParcels() {
        return parcels;
    }

    public void setParcels(List<Parcel> parcels) {
        this.parcels = parcels;
    }

    // Instance method to save this shipment to the database
    public void save(Connection conn) throws SQLException {
        PreparedStatement stmt;
        if (id == 0) {
            // This is a new shipment, so insert it into the database
            stmt = conn.prepareStatement("INSERT INTO Shipment (sender_id, receiving_address, receiving_zip," +
                            " receiver_name, payer_id, total_cost) VALUES (?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setLong(1, sender.getId());
            stmt.setString(2, receiving_address);
            stmt.setString(3, receiving_zip);
            stmt.setString(4, receiver_name);
            stmt.setLong(5, payer.getId());
            stmt.setDouble(6, totalCost);
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getLong(1);
                }
            }
            // Save all parcels associated with this shipment
            for (Parcel parcel : parcels) {
                parcel.setShipment(this);
                parcel.save(conn);
            }
        } else {
            // This is an existing shipment, so update it in the database
            stmt = conn.prepareStatement("UPDATE Shipment SET sender_id = ?, receiving_address = ?," +
                    " receiving_zip = ?, receiver_name = ? payer_id = ?, total_cost = ? WHERE id = ?");
            stmt.setLong(1, sender.getId());
            stmt.setString(2, receiving_address);
            stmt.setString(3, receiving_zip);
            stmt.setString(4, receiver_name);
            stmt.setLong(5, payer.getId());
            stmt.setDouble(6, totalCost);
            stmt.setLong(7, id);
            stmt.executeUpdate();
            // Update all parcels associated with this shipment
            for (Parcel parcel : parcels) {
                parcel.save(conn);
            }
        }
    }

    // Instance method to delete this shipment from the database
    public void delete(Connection conn) throws SQLException {
        if (id > 0) {
            // Delete all parcels associated with this shipment
            for (Parcel parcel : parcels) {
                parcel.delete(conn);
            }
            // Delete the shipment itself
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Shipment WHERE id = ?");
            stmt.setLong(1, id);
            stmt.executeUpdate();
            id = 0L;
        }
    }

    // Static method to retrieve a shipment from the database given an ID
    public static Shipment getShipmentById(int shipmentId, CustomerService customerService, Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Shipment WHERE id = ?");
        stmt.setInt(1, shipmentId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Customer sender = customerService.getCustomerById(rs.getInt("sender_id"), conn);
            String address = rs.getString("receiving_address");
            String zip = rs.getString("receiving_zip");
            String name = rs.getString("receiver_name");
            Customer payer = customerService.getCustomerById(rs.getInt("payer_id"), conn);
            Shipment shipment = new Shipment(rs.getLong("id"), sender, address, zip, name,
                    payer, rs.getDouble("total_cost"));
            // Retrieve all parcels associated with this shipment
            stmt = conn.prepareStatement("SELECT * FROM Parcel WHERE shipment_id = ?");
            stmt.setInt(1, shipmentId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Parcel parcel = new Parcel(rs.getLong("id"), rs.getDouble("weight"));
                shipment.getParcels().add(parcel);
            }
            return shipment;
        }
        return null;
    }
}
