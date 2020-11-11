package com.hotelsystem.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id_payment;

    @Column(nullable = false, name = "created_at")
    private Date created_at;

    @Column(nullable = false, name = "card", length = 100)
    private String card;

    @Column(nullable = false, name = "card_number", length = 50)
    private String card_number;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_booking")
    private Booking booking;
}
