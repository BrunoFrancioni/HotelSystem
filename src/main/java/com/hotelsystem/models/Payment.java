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


    @Column(nullable = false, name = "created_at")
    @Basic
    @Temporal(TemporalType.DATE)
    private java.util.Date created_at;

    @Column(nullable = false, name = "card", length = 100)
    private String card;

    @Column(nullable = false, name = "card_number", length = 50)
    private String card_number;

    @Column(nullable = false, name = "cvv", length = 3)
    private String cvv;

    @Column(nullable = false, name = "holder", length = 100)
    private String holder;

    @Column(nullable = false, name = "month_exp", length = 2)
    private String month_exp;

    @Column(nullable = false, name = "year_exp", length = 4)
    private String year_exp;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_booking")
    private Booking booking;

    public Payment() {
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

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public String getMonth_exp() {
        return month_exp;
    }

    public void setMonth_exp(String month_exp) {
        this.month_exp = month_exp;
    }

    public String getYear_exp() {
        return year_exp;
    }

    public void setYear_exp(String year_exp) {
        this.year_exp = year_exp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return id_payment.equals(payment.id_payment) && created_at.equals(payment.created_at) && card.equals(payment.card) && card_number.equals(payment.card_number) && cvv.equals(payment.cvv) && holder.equals(payment.holder) && month_exp.equals(payment.month_exp) && year_exp.equals(payment.year_exp) && booking.equals(payment.booking);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_payment, created_at, card, card_number, cvv, holder, month_exp, year_exp, booking);
    }
}
