package no.ntnu.bachelor_group3.jdbcevaluation;

import no.ntnu.bachelor_group3.jdbcevaluation.Data.Checkpoint;
import no.ntnu.bachelor_group3.jdbcevaluation.Data.Customer;
import no.ntnu.bachelor_group3.jdbcevaluation.Data.Parcel;
import no.ntnu.bachelor_group3.jdbcevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.jdbcevaluation.Data.Terminal;
import no.ntnu.bachelor_group3.jdbcevaluation.Data.ValidPostalCode;
import no.ntnu.bachelor_group3.jdbcevaluation.Services.CheckpointDAO;
import no.ntnu.bachelor_group3.jdbcevaluation.Services.CustomerDAO;
import no.ntnu.bachelor_group3.jdbcevaluation.Services.ParcelDAO;
import no.ntnu.bachelor_group3.jdbcevaluation.Services.ShipmentDAO;
import no.ntnu.bachelor_group3.jdbcevaluation.Services.TerminalDAO;
import no.ntnu.bachelor_group3.jdbcevaluation.Services.ValidPostalCodeDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager implements AutoCloseable {
    private static final String DB_URL = "jdbc:informix-sqli://database:9088/informix_db:INFORMIXSERVER=informix";
    private static final String DB_USER = "informix";
    private static final String DB_PASSWORD = "in4mix";
    private final CustomerDAO customerDAO;
    private final ShipmentDAO shipmentDAO;
    private final ParcelDAO parcelDAO;
    private final CheckpointDAO checkpointDAO;
    private final TerminalDAO terminalDAO;
    private final ValidPostalCodeDAO validPostalCodeDAO;

    private Connection conn;

    public DatabaseManager() throws SQLException {
        customerDAO = new CustomerDAO();
        shipmentDAO = new ShipmentDAO();
        parcelDAO = new ParcelDAO();
        checkpointDAO = new CheckpointDAO();
        terminalDAO = new TerminalDAO();
        validPostalCodeDAO = new ValidPostalCodeDAO();

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

    public Customer getCustomerById(Long customerId) throws SQLException {
        return customerDAO.getCustomerById(customerId, conn);
    }

    public Long saveCustomer(Customer customer) throws SQLException {
        return customerDAO.save(customer, conn);
    }

    public void deleteCustomer(Customer customer) throws SQLException {
        customerDAO.delete(customer, conn);
    }

    public Shipment getShipmentById(Long shipmentId) throws SQLException {
        return shipmentDAO.getShipmentById(shipmentId, customerDAO, conn);
    }

    public Long saveShipment(Shipment shipment) throws SQLException {
        return shipmentDAO.save(shipment, parcelDAO, terminalDAO, conn);
    }
    /*
    public Long saveShipmentWithParcel(Shipment shipment, Parcel parcel) throws  SQLException {
        return shipmentService.save(shipment, parcel, parcelService, terminalService, conn);
    }

     */

    public void deleteShipment(Shipment shipment) throws SQLException {
        shipmentDAO.delete(shipment, parcelDAO, conn);
    }

    public Parcel getParcelById(Long parcelId) throws SQLException {
        return parcelDAO.getParcelById(parcelId, customerDAO, shipmentDAO, conn);
    }

    public Long saveParcel(Parcel parcel, Long shipmentId) throws SQLException {
        return parcelDAO.save(parcel, shipmentId, conn);
    }

    public Terminal getTerminalById(int terminalId) throws SQLException {
        return terminalDAO.getTerminalById(terminalId, conn);
    }

    public void deleteParcel(Parcel parcel) throws SQLException {
        parcelDAO.delete(parcel, conn);
    }

    public void setCheckpointOnParcel(Parcel parcel, Checkpoint checkpoint) throws SQLException {
        checkpoint.setParcel(parcel);

        checkpointDAO.save(checkpoint, conn);
    }

    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();

        String query = "SELECT * FROM customer";
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Long id = rs.getLong("customer_id");
                String address = rs.getString("address");
                String name = rs.getString("name");
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
            Customer sender = getCustomerById(rs.getLong("sender_id"));
            Customer receiver = getCustomerById(rs.getLong("receiver_id"));
            Customer payer = getCustomerById(rs.getLong("payer_id"));

            Shipment shipment = new Shipment(
                    rs.getLong("shipment_id"),
                    sender,
                    receiver,
                    payer,
                    0
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
                        rs.getLong("parcel_id"),
                        rs.getFloat("weight")
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

    public void deleteRowsFromDB() throws SQLException {
        execute("DELETE FROM valid_postal_codes");

        execute("DELETE FROM checkpoint");
        //execute("ALTER SEQUENCE checkpoint_checkpoint_id_seq RESTART WITH 1");

        execute("DELETE FROM parcel");
        //execute("ALTER SEQUENCE parcel_parcel_id_seq RESTART WITH 1");

        execute("DELETE FROM shipment");
        //execute("ALTER SEQUENCE shipment_shipment_id_seq RESTART WITH 1");

        execute("DELETE FROM customer");
        //execute("ALTER SEQUENCE customer_customer_id_seq RESTART WITH 1");
    }

    public void saveTerminal(Terminal terminal) throws SQLException {
        terminalDAO.save(terminal, conn);
    }

    public void savePostalCode(ValidPostalCode postalCode) throws SQLException {
        validPostalCodeDAO.save(postalCode, conn);
    }

    public Terminal getTerminalByZip(String zipCode) {
        return terminalDAO.getTerminalByZip(zipCode, conn);
    }

    public void setCheckpointOnParcels(Shipment shipment, Checkpoint checkpoint) throws SQLException {
        for (Parcel parcel : shipment.getParcels()) {

            checkpoint.setParcel(parcel);
            checkpointDAO.save(checkpoint, conn);
        }
    }

    public List<Checkpoint> getCheckpointsOnParcel(Long parcelId) {
        return checkpointDAO.getCheckpointsByParcelId(parcelId, parcelDAO, shipmentDAO, customerDAO, terminalDAO, conn);
    }

    public List<Terminal> getAllTerminals() throws SQLException {
        List<Terminal> terminals = new ArrayList<>();

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM terminal");
        while (rs.next()) {
            Long id = rs.getLong("terminal_id");
            String address = rs.getString("address");
            Terminal terminal = new Terminal(id, address);

            terminals.add(terminal);
        }


        return terminals;
    }
}