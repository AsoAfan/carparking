package org.example.models;

import java.util.Date;

public class Reservation {

    private static int id = 0;
    private final int reservationId;
    private Spot spot;
    private User user;
    private float fee;
    private Date start_date;
    private Date end_date;
    private Date updatedAt = new Date();

    public Reservation(Spot spot, User user, float fee, Date start_date, Date end_date) {
        this.reservationId = ++id;
        this.spot = spot;
        this.user = user;
        this.fee = fee;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public int getReservationId() {
        return reservationId;
    }


    public Spot getSpot() {
        return spot;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
        setUpdatedAt(new Date());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        setUpdatedAt(new Date());
    }

    public String getFee() {
        return String.format("%.2f", this.fee);
    }

    public void setFee(float fee) {
        this.fee = fee;
        setUpdatedAt(new Date());
    }

    // TODO: format returned date as you want
    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
        setUpdatedAt(new Date());
    }

    // TODO: format returned date as you want
    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
        setUpdatedAt(new Date());
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
        return "Reservation{" +
                "reservationId=" + getReservationId() +
                ", spot=" + getSpot() +
                ", user=" + getUser() +
                ", fee=" + getFee() +
                ", start_date=" + getStart_date() +
                ", end_date=" + getUser() +
                ", updatedAt=" + getUpdatedAt() +
                '}';
    }
}
