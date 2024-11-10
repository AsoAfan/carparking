package org.example.app.models;

import org.example.app.Database;

import java.util.Date;

public class CarParking implements Table {

    private static int id = 0;
    private final int parkingId;
    private User user;
    private Car car;
    private Spot spot;
    private double price;
    private Date entryTime;
    private Date expectedExitTime;
    private Date exitTime;

    // Constructor for creating a new CarParking instance
    public CarParking(User user, Car car, Spot spot, Date entryTime, Date expectedExitTime) {
        this.parkingId = ++id;
        this.user = user;
        this.car = car;
        this.spot = spot;
        this.entryTime = entryTime;
        this.expectedExitTime = expectedExitTime;
    }

    // Database
    public boolean save() {
        String query;
        if (find(parkingId) == null) {
            query = "INSERT INTO car_parking (user_id, car_id, spot_id, entry_time, expected_exit_time, exit_time) VALUES (?, ?, ?, ?, ?, ?)";
            return Database.executeUpdate(query, user.getId(), car.getCarId(), spot.getSpotNumber(), entryTime, expectedExitTime, exitTime) > 0;
        }
        query = "UPDATE car_parking SET user_id = ?, car_id = ?, spot_id = ?, entry_time = ?, expected_exit_time = ?, exit_time = ? WHERE id = ?";
        boolean result = Database.executeUpdate(query, user.getId(), car.getCarId(), spot.getSpotNumber(), entryTime,
                expectedExitTime,
                exitTime, id) > 0;
        if (result) {
            updateEntities(user, car, spot, entryTime, expectedExitTime, exitTime);
        }
        return result;

    }

    private void updateEntities(User user, Car car, Spot spot, Date entryTime, Date expectedExitTime, Date exitTime) {
        this.user = user;
        this.car = car;
        this.spot = spot;
        this.entryTime = entryTime;
        this.expectedExitTime = expectedExitTime;
        this.exitTime = exitTime;
    }

    public boolean create(User user, Car car, Spot spot, Date entryTime, Date expectedExitTime) {
        String query = "INSERT INTO car_parking (user_id, car_id, spot_id, entry_time, expected_exit_time, exit_time) VALUES (?, ?, ?, ?, ?, ?)";
        return Database.executeUpdate(query, user.getId(), car.getCarId(), spot.getSpotNumber(), entryTime, expectedExitTime, exitTime) > 0;
    }

    public static CarParking find(int parkingId) {
        String query = "SELECT * FROM car_parking WHERE parking_id = ?";
        return Database.executeQuery(query, parkingId, rs -> {
            if (rs.next()) {
                User user = User.find(rs.getInt("user_id"));
                Car car = Car.find(rs.getInt("car_id"));
                Spot spot = Spot.find(rs.getInt("spot_id"));
                Date entryTime = rs.getTimestamp("entry_time");
                Date expectedExitTime = rs.getTimestamp("expected_exit_time");
                CarParking carParking = new CarParking(user, car, spot, entryTime, expectedExitTime);
                carParking.setExitTime(rs.getTimestamp("exit_time"));
                return carParking;
            }
            return null;
        });
    }

    public boolean updateExitTime(Date newExitTime) {
        this.exitTime = newExitTime;
        String query = "UPDATE car_parking SET exit_time = ?, updated_at = ? WHERE parking_id = ?";
        return Database.executeUpdate(query, exitTime, new Date(), parkingId) > 0;
    }

    public boolean delete() {
        String query = "DELETE FROM car_parking WHERE parking_id = ?";
        return Database.executeUpdate(query, parkingId) > 0;
    }

    // Getters and Setters
    public int getParkingId() {
        return parkingId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Spot getSpot() {
        return spot;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    public Date getExpectedExitTime() {
        return expectedExitTime;
    }

    public void setExpectedExitTime(Date expectedExitTime) {
        this.expectedExitTime = expectedExitTime;
    }

    public Date getExitTime() {
        return exitTime;
    }

    public void setExitTime(Date exitTime) {
        this.exitTime = exitTime;
    }

    @Override
    public String toString() {
        return "CarParking{" +
                "parkingId=" + parkingId +
                ", user=" + user +
                ", car=" + car +
                ", spot=" + spot +
                ", price=" + price +
                ", entryTime=" + entryTime +
                ", expectedExitTime=" + expectedExitTime +
                ", exitTime=" + exitTime +
                '}';
    }
}
