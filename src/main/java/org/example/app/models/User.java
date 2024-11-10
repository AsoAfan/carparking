package org.example.app.models;

import org.example.app.Database;
import org.example.app.enums.ROLES;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class User implements Table {

    private static int id = 0;
    private int userId;
    private String username;
    private String password;
    private String phoneNumber;
    private ROLES role = ROLES.USER;
    private boolean isBlocked = false;
    private final Date createdAt = new Date();
    private Date updatedAt = new Date();
    private String email;

    public User(String username, String email, String phoneNumber, String password) {
        this.userId = ++id;
        this.username = username;
        this.email = email;
        setPhoneNumber(phoneNumber);
        setPassword(password);
    }

    public User(String username, String email, String phoneNumber, String password, String role) {
        this(username, email, phoneNumber, password);
        setRole(ROLES.valueOf(role));
    }

    public User(String username, String password, String phoneNumber, ROLES role) {
        this.userId = ++id;
        this.username = username;
        this.password = password;
        setRole(role);
        setPhoneNumber(phoneNumber);
    }

    public User(String username, String password, String phoneNumber, ROLES role, String email, int id) {
        this(username, password, phoneNumber, role);
        this.userId = id;
    }

    @Override
    public boolean save() {
        String query;
        if (find(userId) == null) {
            query = "INSERT INTO users (username, password, phone_number, role, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";
            return Database.executeUpdate(query, this.username, this.password, this.phoneNumber, this.role, this.createdAt, this.updatedAt) > 0;
        }
        query = "UPDATE users SET name = ?, email = ?, phone_number = ?, password = ?, role = ?, updated_at = ? WHERE " +
                "user_id = ?";
        boolean result = Database.executeUpdate(query, this.username, this.password, this.phoneNumber, this.role, this.updatedAt) > 0;
        if (result) {
            updateEntities(username, password, phoneNumber, role, updatedAt);
        }
        return result;
    }

    @Override
    public boolean delete() {
        String query = "DELETE FROM users WHERE user_id = ?";
        return Database.executeUpdate(query, this.userId) > 0;
    }

    private void updateEntities(String username, String password, String phoneNumber, ROLES role, Date updatedAt) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.updatedAt = updatedAt;
    }

    public static User create(String username, String password, String phoneNumber, ROLES role) {
        String query = "INSERT INTO users (username, password, phone_number, role, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";
        int id = Database.executeUpdate(query, username, password, phoneNumber, role, new Date(), new Date());
        return new User(username, password, phoneNumber, role);
    }

    public static User find(int userId) {
        String query = "SELECT * FROM users WHERE user_id = ?";
        return Database.executeQuery(query, userId, rs -> {
            if (rs.next()) {
                return new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("phone_number"),
                        ROLES.valueOf(rs.getString("role"))
                );
            }
            return null;
        });
    }


    /*public boolean update(String username, String password, String phoneNumber, ROLES role) {
        String query = "UPDATE users SET name = ?, email = ?, phone_number = ?, password = ?, role = ?, updated_at = ? " +
                "WHERE" +
                " user_id = ?";
        Database.executeUpdate(query, username, password, phoneNumber, role.name, this.createdAt, this.updatedAt);
    }
*/
    public int getId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public ROLES getRole() {
        return role;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void block() {
        this.isBlocked = true;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (validatePhoneNumber()) {
            this.phoneNumber = phoneNumber;
        }
        System.out.println("Invalid phone number: " + phoneNumber);
        System.out.println("Resetting phone number...");
    }

    public void unblock() {
        this.isBlocked = false;
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

    public boolean validatePhoneNumber() {
        return Pattern.compile("\\+?[0-9]*").matcher(this.phoneNumber).matches();
    }

    public static boolean isPhoneNumberValid(String phoneNumber) {
        return Pattern.compile("^[0-9]+$").matcher(phoneNumber).matches();
    }

    public static boolean isPasswordValid(String password) {
        return password.length() >= 8 &&
                Pattern.compile("[A-Z]").matcher(password).find() &&
                Pattern.compile("[0-9]").matcher(password).find();
    }

    public List<Reservation> getReservations() {
        String query = "SELECT * FROM reservations WHERE user_id = ?";
        List<Reservation> reservations = new ArrayList<>();

        Database.executeQuery(query, this.userId, rs -> {
            while (rs.next()) {
                Reservation reservation = Reservation.fromResultSet(rs);
                reservations.add(reservation);
            }
            return null;
        });

        return reservations;
    }

    public void setUsername(String username) {
        this.username = username;
        setUpdatedAt(new Date());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        setUpdatedAt(new Date());
    }

    public void setPassword(String password) {
        // TODO: Use encryption if you want
//        String encrypted =
        this.password = password;
    }


    public void setRole(ROLES role) {
        if (role != ROLES.USER && role != ROLES.ADMIN) {
            return; // Invalid role, do nothing
        }
        this.role = role;
        setUpdatedAt(new Date());
    }


    public int getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + getUserId() +
                ", username='" + getUsername() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", phoneNumber='" + getPhoneNumber() + '\'' +
                ", role=" + getRole() +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdatedAt() +
                '}';
    }
}
