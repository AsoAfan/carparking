package org.example.models;

import org.example.enums.ROLES;
import java.util.Date;
import java.util.regex.Pattern;

public class User {

    private static int id = 0;
    private final int userId;
    private String username;
    private String email;
    private String phoneNumber;
    private String password;
    private ROLES role = ROLES.USER;
    private final Date createdAt = new Date();
    private Date updatedAt = new Date();

    public User(String username, String email, String phoneNumber, String password) {
        this.userId = ++id;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        setPassword(password);
    }

    public User(String username, String email, String phoneNumber, String password, String role) {
        this(username, email, phoneNumber, password);
        setRole(ROLES.valueOf(role));
    }

    public String getUsername() {
        return username;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        setUpdatedAt(new Date());
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {

    }

    public static boolean isPasswordValid(String password) {
        return password.length() >= 8 &&
                Pattern.compile("[A-Z]").matcher(password).find() &&
                Pattern.compile("[0-9]").matcher(password).find();
    }

    public static boolean isPhoneNumberValid(String phoneNumber) {
        return Pattern.compile("^[0-9]+$").matcher(phoneNumber).matches();
    }

    public String getRole() {
        return role.name;
    }

    public void setRole(ROLES role) {
        if (role != ROLES.USER && role != ROLES.ADMIN) {
            return; // Invalid role, do nothing
        }
        this.role = role;
        setUpdatedAt(new Date());
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
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
