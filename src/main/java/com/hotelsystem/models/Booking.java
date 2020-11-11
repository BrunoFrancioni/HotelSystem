package com.hotelsystem.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id_booking;

    @Column(nullable = false, name = "check_in")
    private Date check_in;

    @Column(nullable = false, name = "check_out")
    private Date check_out;

    @Column(nullable = false, name = "created_at")
    private Date created_at;

    @Column(nullable = false, name = "breakfast_included")
    private Boolean breakfast_included;

    @Column(nullable = false, name = "parking")
    private Boolean parking;

    @Column(nullable = false, name = "free_cancellation")
    private Boolean free_cancellation;

    @Column(nullable = false, name = "cost")
    private Double cost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guest")
    private User guest;

    /*public Booking() {
    }

    public Booking(Long id_booking, Date check_in, Date check_out, Date created_at, Boolean breakfast_included, Boolean parking, Boolean free_cancellation, Double cost, Room room, User guest) {
        this.id_booking = id_booking;
        this.check_in = check_in;
        this.check_out = check_out;
        this.created_at = created_at;
        this.breakfast_included = breakfast_included;
        this.parking = parking;
        this.free_cancellation = free_cancellation;
        this.cost = cost;
        this.room = room;
        this.guest = guest;
    }*/

    public Long getId_booking() {
        return id_booking;
    }

    public void setId_booking(Long id_booking) {
        this.id_booking = id_booking;
    }

    public Date getCheck_in() {
        return check_in;
    }

    public void setCheck_in(Date check_in) {
        this.check_in = check_in;
    }

    public Date getCheck_out() {
        return check_out;
    }

    public void setCheck_out(Date check_out) {
        this.check_out = check_out;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Boolean getBreakfast_included() {
        return breakfast_included;
    }

    public void setBreakfast_included(Boolean breakfast_included) {
        this.breakfast_included = breakfast_included;
    }

    public Boolean getParking() {
        return parking;
    }

    public void setParking(Boolean parking) {
        this.parking = parking;
    }

    public Boolean getFree_cancellation() {
        return free_cancellation;
    }

    public void setFree_cancellation(Boolean free_cancellation) {
        this.free_cancellation = free_cancellation;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public User getGuest() {
        return guest;
    }

    public void setGuest(User guest) {
        this.guest = guest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return id_booking.equals(booking.id_booking) &&
                check_in.equals(booking.check_in) &&
                check_out.equals(booking.check_out) &&
                created_at.equals(booking.created_at) &&
                breakfast_included.equals(booking.breakfast_included) &&
                parking.equals(booking.parking) &&
                free_cancellation.equals(booking.free_cancellation) &&
                cost.equals(booking.cost) &&
                room.equals(booking.room) &&
                guest.equals(booking.guest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_booking, check_in, check_out, created_at, breakfast_included, parking, free_cancellation, cost, room, guest);
    }
}
