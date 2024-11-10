package org.example.app.models;

import org.example.app.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Reservation implements Table {

    private Integer id;
    private Spot spot;
    private User user;
    private float fee;
    private Date startDate;
    private Date endDate;

    public Reservation(Spot spot, User user, float fee, Date startDate, Date endDate, Integer id) {
        this.spot = spot;
        this.user = user;
        this.fee = fee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.id = id;
    }

    public static Reservation findById(int id) {
        String query = "SELECT * FROM reservations WHERE id = ?";
        return Database.executeQuery(query, id, rs -> {
            if (rs.next()) {
                return fromResultSet(rs);
            }
            return null;
        });
    }

    public boolean save() {
        String query;
        if (findById(id) == null) {
            query = "INSERT INTO reservations (spot_id, user_id, fee, start_date, end_date) VALUES (?, ?, ?, ?, ?)";
            int generatedId = Database.executeUpdate(
                    query,
                    this.spot.getSpotNumber(),
                    this.user.getId(),
                    this.fee,
                    this.startDate,
                    this.endDate
            );
            generateId(generatedId);
            return generatedId > 0;
        }
        query = "UPDATE reservations SET spot_id = ?, user_id = ?, fee = ?, start_date = ?, end_date = ? WHERE id = ?";
        boolean result = Database.executeUpdate(query, spot.getSpotNumber(), user.getId(), fee, startDate, endDate, id) > 0;
        if (result) {
            updateEntities(spot, user, fee, startDate, endDate);
        }
        return result;
    }

    private void updateEntities(Spot spot, User user, float fee, Date startDate, Date endDate) {
        this.spot = spot;
        this.user = user;
        this.fee = fee;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void generateId(int generatedId) {
        if (generatedId > 0) {
            this.id = generatedId;
        }
    }

    private boolean isExist() {
        return id != null;
    }

    public boolean create(Spot spot, User user, float fee, Date startDate, Date endDate) {
        String query = "INSERT INTO reservations (spot_id, user_id, fee, start_date, end_date) VALUES (?, ?, ?, ?, ?)";
        int generatedId = Database.executeUpdate(query, spot.getSpotNumber(), user.getId(), fee, startDate, endDate);
        generateId(generatedId);
        return generatedId > 0;
    }

    /*public boolean update() {
        if (id == null) return false;
        String query = "UPDATE reservations SET spot_id = ?, user_id = ?, fee = ?, start_date = ?, end_date = ? WHERE id = ?";
        return Database.executeUpdate(query, spot.getSpotNumber(), user.getId(), fee, startDate, endDate, id) > 0;
    }*/

    public boolean delete() {
        if (id == null) return false;
        String query = "DELETE FROM reservations WHERE id = ?";
        return Database.executeUpdate(query, id) > 0;
    }

    public Spot getRelatedSpot() {
        return Spot.find(this.spot.getSpotNumber());
    }

    public User getRelatedUser() {
        return User.find(this.user.getId());
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Spot getSpot() {
        return spot;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    static Reservation fromResultSet(ResultSet rs) throws SQLException {
        Spot spot = Spot.find(rs.getInt("spot_id")); // Retrieve Spot using Spot's find method
        User user = User.find(rs.getInt("user_id")); // Retrieve User using User's find method
        return new Reservation(
                spot,
                user,
                rs.getFloat("fee"),
                rs.getDate("start_date"),
                rs.getDate("end_date"),
                rs.getInt("id")
        );
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", spot=" + spot +
                ", user=" + user +
                ", fee=" + fee +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
