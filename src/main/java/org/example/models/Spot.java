package org.example.models;

import java.util.Date;

public class Spot {
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
        isAvailable = available;
        setUpdateAt(new Date());
    }

    public void toggleAvailable() {
        isAvailable = !isAvailable;
        setUpdateAt(new Date());
    }

    // TODO: format returned date as you want
    public Date getCreateAt() {
        return createAt;
    }

    // TODO: format returned date as you want
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
