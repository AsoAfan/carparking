package org.example.app.models;

import org.example.app.Database;
import org.example.app.enums.PRIORITY;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ParkingLot implements Table {

    private Integer id;
    private String name;
    private int capacity;
    private PRIORITY priority;

    public ParkingLot(String name, int capacity, PRIORITY priority) {
        this.name = name;
        this.capacity = capacity;
        this.priority = priority;
    }

    public ParkingLot(String name, int capacity, PRIORITY priority, Integer id) {
        this.name = name;
        this.capacity = capacity;
        this.priority = priority;
        this.id = id;
    }

    // Database
    public boolean save() {
        String query;
        if (findById(id) == null) {
            query = "INSERT INTO parking_lots (name, capacity, priority) VALUES (?, ?, ?)";
            return Database.executeUpdate(query, name, capacity, priority) > 0;
        }
        query = "UPDATE parking_lots SET name = ?, capacity = ?, priority = ? WHERE id = ?";
        boolean result = Database.executeUpdate(query, name, capacity, priority, id) > 0;
        if (result) {
            updateEntities(name, capacity, priority);
        }
        return result;
    }

    private void updateEntities(String name, int capacity, PRIORITY priority) {
        this.name = name;
        this.capacity = capacity;
        this.priority = priority;
    }

    public boolean create(String name, int capacity, PRIORITY priority) {
        String query = "INSERT INTO parking_lots (name, capacity, priority) VALUES (?, ?, ?)";
        return Database.executeUpdate(query, name, capacity, priority.name()) > 0;
    }


   /* public boolean update() {
        if (id == null) return false;
        String query = "UPDATE parking_lots SET name = ?, capacity = ?, priority = ? WHERE id = ?";
        return Database.executeUpdate(query, name, capacity, priority, id) > 0;
    }*/

    public boolean delete() {
        if (id == null) return false;
        String query = "DELETE FROM parking_lots WHERE id = ?";
        return Database.executeUpdate(query, id) > 0;
    }

    public static ParkingLot findById(int id) {
        String query = "SELECT * FROM parking_lots WHERE id = ?";
        return Database.executeQuery(query, id, rs -> {
            if (rs.next()) {
                return fromResultSet(rs);
            } else {
                return null;
            }
        });
    }

    public static List<ParkingLot> getAll() {
        String query = "SELECT * FROM parking_lots";
        return Database.executeQuery(query, rs -> {
            List<ParkingLot> parkingLots = new ArrayList<>();
            while (rs.next()) {
                parkingLots.add(fromResultSet(rs));
            }
            return parkingLots;
        });
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public PRIORITY getPriority() {
        return priority;
    }

    public void setPriority(PRIORITY priority) {
        this.priority = priority;
    }

    public static ParkingLot fromResultSet(ResultSet rs) throws SQLException {
        return new ParkingLot(
                rs.getString("name"),
                rs.getInt("capacity"),
                (PRIORITY) rs.getObject("priority"),
                rs.getInt("id")
        );
    }
}
