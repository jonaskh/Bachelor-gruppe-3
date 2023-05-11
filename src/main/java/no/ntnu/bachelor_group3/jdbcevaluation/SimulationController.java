/*
package no.ntnu.bachelor_group3.jdbcevaluation;

import no.ntnu.bachelor_group3.jdbcevaluation.Data.Checkpoint;
import no.ntnu.bachelor_group3.jdbcevaluation.Data.Customer;
import no.ntnu.bachelor_group3.jdbcevaluation.Data.Parcel;
import no.ntnu.bachelor_group3.jdbcevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.jdbcevaluation.Data.Terminal;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class SimulationController {

    private Long id = 1L;
    private void createShipmentWithParcels() {

    }


    private void setCheckpointOnParcelsInShipment(Long shipmentId, int terminalZip, String location, double cost) {
        try (DatabaseManager db = new DatabaseManager()) {
            Shipment shipment = db.getShipmentById(shipmentId);
            List<Parcel> parcels = db.getParcelsForShipment(shipment);
            Terminal terminal = db.getTerminalById(terminalZip);
            for (Parcel parcel: parcels) {
                Checkpoint checkpoint = new Checkpoint(0L, cost, location, parcel, terminal, );

                db.setCheckpointOnParcel(parcel, checkpoint);
                db.commit();
            }
        } catch (SQLException e) {
            System.err.println("Error setting checkpoint" + e.getMessage());
            e.printStackTrace();
        }
    }

    private void createAndSaveShipment(Long senderId, Long payerId, Long receiverId, double cost, List<Parcel> parcels) {
        try (DatabaseManager db = new DatabaseManager()) {
            Customer sender = db.getCustomerById(senderId);
            Customer payer = db.getCustomerById(payerId);
            Customer receiver = db.getCustomerById(receiverId);
            Shipment shipment = new Shipment(0L, sender, receiver, payer, cost);
            shipment.setParcels(parcels);
            db.saveShipment(shipment);
            db.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Parcel> createParcels(int amount) {
        List<Parcel> parcels = new ArrayList<>();
        Random random = new Random();
        try (DatabaseManager db = new DatabaseManager()) {
            for(int i = 0; i < amount; i++) {
                double weight = random.nextDouble(0, 50);
                Parcel parcel = new Parcel(0L, weight);
                parcels.add(parcel);
                db.saveParcel(parcel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return parcels;
    }

    public void createShipmentProcess() {
        createAndSaveShipment(1L, 1L, 1L, 12.5, createParcels(1));
    }

    public void runSixCheckpointsOnShipment() {

        for (int i = 0; i < 6; i++) {
            setCheckpointOnParcelsInShipment(id, 1, "Bøgata 6008", 10);
        }
        id++;
    }

    public void simulateProcess2() {
        setCheckpointOnParcelsInShipment(2L, 1, "Bøgata 6008", 15);
    }

    public void simulateProcess3() {
        setCheckpointOnParcelsInShipment(3L, 1, "Trondheim", 7.5);
    }

    public void readTerminalFromZipCode() {
        try (DatabaseManager db = new DatabaseManager()) {
            Terminal terminal = db.getTerminalByZip("6008");
            System.out.println("ID: " + terminal.getId()+ ", Address: " + terminal.getLocation());
        } catch (Exception e) {

        }
    }

}

 */
