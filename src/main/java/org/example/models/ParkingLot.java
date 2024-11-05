package org.example.models;

import org.example.enums.PRIORITY;

import java.io.Serializable;
import java.util.Date;

public class ParkingLot implements Serializable {

    private static int id = 0;
    private final int lotId;
    private String name;
    private int capacity;
    private PRIORITY priority;
    private final Date createdAt = new Date();
    private Date updatedAt = new Date();

    public ParkingLot(String name, int capacity, PRIORITY priority) {
        this.lotId = ++id;
        this.name = name;
        setCapacity(capacity);
        this.priority = priority != null ? priority : PRIORITY.NORMAL;
    }

    public int getLotId() {
        return lotId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        setUpdatedAt();
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        if (capacity < 0) {
            // TODO: handle invalid capacity number
            return;
        }
        this.capacity = capacity;
        setUpdatedAt();
    }

    public PRIORITY getPriority() {
        return priority;
    }

    public void setPriority(PRIORITY priority) {
        if (priority != PRIORITY.NORMAL && priority != PRIORITY.VIP) {
            // TODO: handle invalid priority
            return;
        }
        this.priority = priority;
        setUpdatedAt();
    }

    // TODO: format returned date as you want
    public Date getCreatedAt() {
        return createdAt;
    }

    // TODO: format returned date as you want
    public Date getUpdatedAt() {
        return updatedAt;
    }

    private void setUpdatedAt() {
        this.updatedAt = new Date();
    }

    @Override
    public String toString() {
        return "ParkingLot{" +
                "lotId=" + getLotId() +
                ", name='" + getName() + '\'' +
                ", capacity=" + getCapacity() +
                ", priority=" + getPriority().toString() +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdatedAt() +
                '}';
    }
}
