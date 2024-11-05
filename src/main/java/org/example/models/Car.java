package org.example.models;

import java.util.Date;

public class Car {

    private static int id = 0;

    private final int carId;
    private String plateNumber;
    private User user;
    private final Date createdAt = new Date();
    private Date updatedAt = new Date();

    public Car(String plateNumber, User user) {
        carId = ++id;
        this.plateNumber = plateNumber;
        this.user = user;
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

    // TODO: format returned date as you want
    public Date getCreatedAt() {
        return createdAt;
    }

    // TODO: format returned date as you want
    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carId=" + getCarId() +
                ", plateNumber='" + getPlateNumber() + '\'' +
                ", user=" + getUser() +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdatedAt() +
                '}';
    }
}
