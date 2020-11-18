package com.hotelsystem.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "cancellation")
public class Cancellation {
    @Id
    @GeneratedValue(generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id_cancellation;

    @Column(nullable = false, name = "created_at")
    @Basic
    @Temporal(TemporalType.DATE)
    private java.util.Date created_at;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_booking")
    private Booking booking;

    @Version
    private Integer version;

    public Cancellation() {
    }

    public Cancellation(Long id_cancellation, Date created_at, Booking booking) {
        this.id_cancellation = id_cancellation;
        this.created_at = created_at;
        this.booking = booking;
    }

    public Long getId_cancellation() {
        return id_cancellation;
    }

    public void setId_cancellation(Long id_cancellation) {
        this.id_cancellation = id_cancellation;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Integer getVersion() { return version; }

    public void setVersion(Integer version) { this.version = version; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cancellation cancellation = (Cancellation) o;
        return id_cancellation.equals(cancellation.id_cancellation) &&
                created_at.equals(cancellation.created_at) &&
                booking.equals(cancellation.booking) &&
                version.equals(cancellation.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_cancellation, created_at, booking, version);
    }
}
