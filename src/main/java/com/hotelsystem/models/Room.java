package com.hotelsystem.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.hotelsystem.models.Booking;
import org.springframework.beans.factory.annotation.Value;

@Entity
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id_room;

    @Column(nullable = false, length = 100, name = "name", unique = true)
    private String name;

    @Column(nullable = false, name = "price")
    private Double price;

    @Column(nullable = false, name = "occupancy")
    private Integer occupancy;

    @Column(nullable = false, name = "availability")
    private Boolean availability;

    @Column(nullable = true, name = "facilities")
    private String facilities;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private final List<Booking> bookings = new ArrayList<Booking>();

    @Version
    private Integer version;

    public Room() {
    }

    public Room(Long id_room, String name, Double price, Integer occupancy, Boolean availability, String facilities, Integer version) {
        this.id_room = id_room;
        this.name = name;
        this.price = price;
        this.occupancy = occupancy;
        this.availability = availability;
        this.facilities = facilities;
        this.version = version;
    }

    public Long getId_room() {
        return id_room;
    }

    public void setId_room(Long id_room) {
        this.id_room = id_room;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(Integer occupancy) {
        this.occupancy = occupancy;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public String getFacilities() {
        return facilities;
    }

    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }

    public Integer getVersion() { return version; }

    public void setVersion(Integer version) { this.version = version; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return id_room.equals(room.id_room) &&
                name.equals(room.name) &&
                price.equals(room.price) &&
                occupancy.equals(room.occupancy) &&
                availability.equals(room.availability) &&
                Objects.equals(facilities, room.facilities) &&
                version.equals(room.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_room, name, price, occupancy, availability, facilities, version);
    }
}
