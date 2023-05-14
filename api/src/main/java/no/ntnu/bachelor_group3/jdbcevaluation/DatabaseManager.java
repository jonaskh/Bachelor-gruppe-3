package no.ntnu.bachelor_group3.jdbcevaluation;

import no.ntnu.bachelor_group3.jdbcevaluation.Data.Checkpoint;
import no.ntnu.bachelor_group3.jdbcevaluation.Data.Customer;
import no.ntnu.bachelor_group3.jdbcevaluation.Data.Parcel;
import no.ntnu.bachelor_group3.jdbcevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.jdbcevaluation.Data.Terminal;
import no.ntnu.bachelor_group3.jdbcevaluation.Data.ValidPostalCode;
import no.ntnu.bachelor_group3.jdbcevaluation.Services.CheckpointService;
import no.ntnu.bachelor_group3.jdbcevaluation.Services.CustomerService;
import no.ntnu.bachelor_group3.jdbcevaluation.Services.ParcelService;
import no.ntnu.bachelor_group3.jdbcevaluation.Services.ShipmentService;
import no.ntnu.bachelor_group3.jdbcevaluation.Services.TerminalService;
import no.ntnu.bachelor_group3.jdbcevaluation.Services.ValidPostalCodeService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager implements AutoCloseable {
    private static final String DB_URL = "jdbc:postgresql://database:5432/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";
    private final CustomerService customerService;
    private final ShipmentService shipmentService;
    private final ParcelService parcelService;
    private final CheckpointService checkpointService;
    private final TerminalService terminalService;
    private final ValidPostalCodeService validPostalCodeService;

    private Connection conn;

    public DatabaseManager() throws SQLException {
        customerService = new CustomerService();
        shipmentService = new ShipmentService();
        parcelService = new ParcelService();
        checkpointService = new CheckpointService();
        terminalService = new TerminalService();
        validPostalCodeService = new ValidPostalCodeService();

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
        return customerService.getCustomerById(customerId, conn);
    }

    public void saveCustomer(Customer customer) throws SQLException {
        customerService.save(customer, conn);
    }

    public void deleteCustomer(Customer customer) throws SQLException {
        customerService.delete(customer, conn);
    }

    public Shipment getShipmentById(Long shipmentId) throws SQLException {
        return shipmentService.getShipmentById(shipmentId, customerService, conn);
    }

    public Long saveShipment(Shipment shipment) throws SQLException {
        return shipmentService.save(shipment, parcelService, terminalService, conn);
    }

    public Long saveShipmentWithParcel(Shipment shipment, Parcel parcel) throws  SQLException {
        return shipmentService.save(shipment, parcel, parcelService, terminalService, conn);
    }

    public void deleteShipment(Shipment shipment) throws SQLException {
        shipmentService.delete(shipment, parcelService, conn);
    }

    public Parcel getParcelById(Long parcelId) throws SQLException {
        return parcelService.getParcelById(parcelId, customerService, shipmentService, conn);
    }

    public Long saveParcel(Parcel parcel, Long shipmentId) throws SQLException {
        return parcelService.save(parcel, shipmentId, conn);
    }

    public Terminal getTerminalById(int terminalId) throws SQLException {
        return terminalService.getTerminalById(terminalId, conn);
    }

    public void deleteParcel(Parcel parcel) throws SQLException {
        parcelService.delete(parcel, conn);
    }

    public void setCheckpointOnParcel(Parcel parcel, Checkpoint checkpoint) throws SQLException {
        checkpoint.setParcel(parcel);

        checkpointService.save(checkpoint, conn);
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

    public void deleteRowsFromDB() throws SQLException {
        execute("DELETE FROM valid_postal_codes");

        execute("DELETE FROM checkpoint");
        execute("ALTER SEQUENCE checkpoint_checkpoint_id_seq RESTART WITH 1");

        execute("DELETE FROM parcel");
        execute("ALTER SEQUENCE parcel_parcel_id_seq RESTART WITH 1");

        execute("DELETE FROM shipment");
        execute("ALTER SEQUENCE shipment_shipment_id_seq RESTART WITH 1");

        execute("DELETE FROM customer");
        execute("ALTER SEQUENCE customer_customer_id_seq RESTART WITH 1");
    }

    public void saveTerminal(Terminal terminal) throws SQLException {
        terminalService.save(terminal, conn);
    }

    public void savePostalCode(ValidPostalCode postalCode) throws SQLException {
        validPostalCodeService.save(postalCode, conn);
    }

    public Terminal getTerminalByZip(String zipCode) {
        return terminalService.getTerminalByZip(zipCode, conn);
    }

    public List<Terminal> getAllTerminals() throws SQLException {
        List<Terminal> terminals = new ArrayList<>();

        String query = "SELECT * FROM terminal";
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Long id = rs.getLong("terminal_id");
                String address = rs.getString("address");

                Terminal terminal = new Terminal(id, address);
                terminals.add(terminal);
            }
        }

        return terminals;
    }

    public void setCheckpointOnParcels(Shipment shipment, Checkpoint checkpoint) throws SQLException {
        for (Parcel parcel : shipment.getParcels()) {

            checkpoint.setParcel(parcel);
            checkpointService.save(checkpoint, conn);
        }
    }

    public List<Checkpoint> getCheckpointsOnParcel(Long parcelId) {
        return checkpointService.getCheckpointsByParcelId(parcelId, parcelService, shipmentService, customerService, terminalService, conn);
    }
}