package org.example.app.models;

import org.example.app.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Car implements Table {

    private int carId;
    private String plateNumber;
    private User user;
    private final Date createdAt;
    private Date updatedAt;

    public Car(String plateNumber, User user) {
        this.plateNumber = plateNumber;
        this.user = user;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public Car(int carId, String plateNumber, User user, Date createdAt, Date updatedAt) {
        this.carId = carId;
        this.plateNumber = plateNumber;
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Database
    public static boolean create(String plateNumber, User user) {
        String query = "INSERT INTO cars (plate_number, user_id, created_at, updated_at) VALUES (?, ?, ?, ?)";
        int result = Database.executeUpdate(query, plateNumber, user.getId(), new Date(), new Date());
        return result > 0;
    }

    public static Car find(int carId) {
        String query = "SELECT * FROM cars WHERE car_id = ?";
        return Database.executeQuery(query, carId, rs -> {
            if (rs.next()) {
                User user = User.find(rs.getInt("user_id")); // Assumes User class has a find method
                return fromResultSet(rs, user);
            }
            return null;
        });
    }

    @Override
    public boolean save() {
        String query;
        if (find(carId) == null) {
            query = "INSERT INTO cars (plate_number, user_id, created_at, updated_at) VALUES (?, ?, ?, ?)";
            return Database.executeUpdate(query, plateNumber, user.getId(), createdAt, updatedAt) > 0;
        }

        query = "UPDATE cars SET plate_number = ?, user_id = ?, updated_at = ? WHERE car_id = ?";

        boolean result = Database.executeUpdate(query, plateNumber, user.getId(), updatedAt, carId) > 0;
        if (result) {
            updateEntities(plateNumber, user, updatedAt);
        }

        return result;
    }

    private void updateEntities(String plateNumber, User user, Date updatedAt) {
        this.plateNumber = plateNumber;
        this.user = user;
        this.updatedAt = updatedAt;
    }

    /*public boolean update() {
        String query = "UPDATE cars SET plate_number = ?, updated_at = ? WHERE car_id = ?";
        int result = Database.executeUpdate(query, plateNumber, new Date(), carId);
        return result > 0;
    }*/

//    public boolean updateCarUser() {
//        String query = "UPDATE cars SET user_id = ? WHERE car_id = ?";
//        int result = Database.executeUpdate(query, user.getId(), carId);
//        return result > 0;
//    }

    public boolean delete() {
        String query = "DELETE FROM cars WHERE car_id = ?";
        int result = Database.executeUpdate(query, carId);
        return result > 0;
    }

    private static Car fromResultSet(ResultSet rs, User user) throws SQLException {
        int carId = rs.getInt("car_id");
        String plateNumber = rs.getString("plate_number");
        Date createdAt = rs.getTimestamp("created_at");
        Date updatedAt = rs.getTimestamp("updated_at");
        return new Car(carId, plateNumber, user, createdAt, updatedAt);
    }

    public int getCarId() {
        return carId;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
        setUpdatedAt(new Date());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        setUpdatedAt(new Date());
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carId=" + carId +
                ", plateNumber='" + plateNumber + '\'' +
                ", user=" + user +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
