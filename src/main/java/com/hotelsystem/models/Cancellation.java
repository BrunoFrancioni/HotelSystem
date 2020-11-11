package com.hotelsystem.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cancellation")
public class Cancellation {
    @Id
    @GeneratedValue(generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id_cancellation;

    @Column(nullable = false, name = "created_at")
    private Date created_at;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_booking")
    private Booking booking;
}
