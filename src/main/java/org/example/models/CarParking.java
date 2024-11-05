package org.example.models;

import java.util.Date;

public class CarParking {

    private static int id = 0;
    private final int parkingId;
    private User user;
    private Car car;
    private Spot spot;
    private double price;
    private Date entryTime;
    private Date expectedExitTime;
    private Date exitTime;

    public CarParking(User user, Car car, Spot spot, Date entryTime, Date expectedExitTime) {
        this.parkingId = ++id;
        this.user = user;
        this.car = car;
        this.spot = spot;
        this.entryTime = entryTime;
        this.expectedExitTime = expectedExitTime;
    }

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
        // TODO: calculate price carefully according to the requirements
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // TODO: format returned date as you want
    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    // TODO: format returned date as you want
    public Date getExpectedExitTime() {
        return expectedExitTime;
    }

    public void setExpectedExitTime(Date expectedExitTime) {
        this.expectedExitTime = expectedExitTime;
    }

    // TODO: format returned date as you want
    public Date getExitTime() {
        return exitTime;
    }

    public void setExitTime(Date exitTime) {
        this.exitTime = exitTime;
    }

    @Override
    public String toString() {
        return "CarParking{" +
                "parkingId=" + getParkingId() +
                ", user=" + getUser() +
                ", car=" + getCar() +
                ", spot=" + getSpot() +
                ", price=" + getPrice() +
                ", entryTime=" + getEntryTime() +
                ", expectedExitTime=" + getExpectedExitTime() +
                ", exitTime=" + getExitTime() +
                '}';
    }
}
