package com.hotelsystem.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id_payment;

    @Version
    private Integer version;

    @Column(nullable = false, name = "created_at")
    @Basic
    @Temporal(TemporalType.DATE)
    private java.util.Date created_at;

    @Column(nullable = false, name = "card", length = 100)
    private String card;

    @Column(nullable = false, name = "card_number", length = 50)
    private String card_number;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_booking")
    private Booking booking;

    public Payment() {
    }

    public Payment(Long id_payment, Date created_at, String card, String card_number, Booking booking) {
        this.id_payment = id_payment;
        this.created_at = created_at;
        this.card = card;
        this.card_number = card_number;
        this.booking = booking;
    }

    public Long getId_payment() {
        return id_payment;
    }

    public void setId_payment(Long id_payment) {
        this.id_payment = id_payment;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {this.booking = booking;}

    public Integer getVersion() {return version;}

    public void setVersion(Integer version) {this.version = version;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return id_payment.equals(payment.id_payment) &&
                created_at.equals(payment.created_at) &&
                card.equals(payment.card) &&
                card_number.equals(payment.card_number) &&
                booking.equals(payment.booking) &&
                version.equals(payment.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_payment, created_at, card, card_number, booking, version);
    }
}
