package org.example.app.models;

import org.example.app.Database;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Spot implements Table {

    private static int counter = 0;
    private final int spotNumber;
    private boolean isAvailable = true;
    private final ParkingLot parkingLot;
    private final Date createAt = new Date();
    private Date updateAt = new Date();

    public Spot(int spotNumber, ParkingLot parkingLot) {
        this.spotNumber = spotNumber;
        this.parkingLot = parkingLot;
    }

    public Spot(ParkingLot parkingLot) {
        this.spotNumber = ++counter;
        this.parkingLot = parkingLot;
    }

    // Database
    public static boolean create(int spotNumber, boolean isAvailable, ParkingLot parkingLot) {
        String query = "INSERT INTO spots (spot_number, is_available, parking_lot_id, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
        return Database.executeUpdate(query, spotNumber, isAvailable, parkingLot.getId(), new Date(), new Date()) > 0;
    }

    public boolean save() {
        String query;
        if (find(spotNumber) == null) {
            query = "INSERT INTO spots (spot_number, is_available, parking_lot_id, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
            return Database.executeUpdate(query, spotNumber, isAvailable, parkingLot.getId(), createAt, updateAt) > 0;
        }
        query = "UPDATE spots SET is_available = ?, updated_at = ? WHERE spot_number = ?";
        boolean result = Database.executeUpdate(query, isAvailable, parkingLot.getId(), new Date(), spotNumber) > 0;
        if (result) {
            updateEntities(isAvailable, updateAt);
        }
        return result;
    }

    private void updateEntities(boolean isAvailable, Date updateAt) {
        this.isAvailable = isAvailable;
        this.updateAt = updateAt;

    }

    public static Spot find(int spotNumber) {
        String query = "SELECT * FROM spots WHERE spot_number = ?";
        return Database.executeQuery(query, spotNumber, rs -> {
            if (rs.next()) {
                ParkingLot parkingLot = ParkingLot.findById(rs.getInt("parking_lot_id"));
                Spot spot = new Spot(rs.getInt("spot_number"), parkingLot);
                spot.setAvailable(rs.getBoolean("is_available"));
                spot.setUpdateAt(rs.getTimestamp("updated_at"));
                return spot;
            }
            return null;
        });
    }

    public boolean updateAvailability(boolean available) {
        this.isAvailable = available;
        this.updateAt = new Date();
        String query = "UPDATE spots SET is_available = ?, updated_at = ? WHERE spot_number = ?";
        return Database.executeUpdate(query, isAvailable, updateAt, spotNumber) > 0;
    }

    public boolean delete() {
        String query = "DELETE FROM spots WHERE spot_number = ?";
        return Database.executeUpdate(query, spotNumber) > 0;
    }

    public List<Reservation> getReservations() {
        String query = "SELECT * FROM reservations WHERE spot_id = ?";
        List<Reservation> reservations = new ArrayList<>();
        Database.executeQuery(query, this.spotNumber, rs -> {
            while (rs.next()) {
                reservations.add(Reservation.fromResultSet(rs));
            }
            return null;
        });
        return reservations;
    }

    public ParkingLot getParking() {
        return ParkingLot.findById(parkingLot.getId());
    }

    // Getters and Setters
    public int getSpotNumber() {
        return spotNumber;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
        setUpdateAt(new Date());
    }

    public void toggleAvailable() {
        this.isAvailable = !this.isAvailable;
        setUpdateAt(new Date());
    }

    public Date getCreateAt() {
        return createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    @Override
    public String toString() {
        return "Spot{" +
                "spotNumber=" + getSpotNumber() +
                ", isAvailable=" + isAvailable() +
                ", parkingLot=" + getParkingLot() +
                ", createAt=" + getCreateAt() +
                ", updateAt=" + getUpdateAt() +
                '}';
    }
}
