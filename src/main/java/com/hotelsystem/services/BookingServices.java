package com.hotelsystem.services;

import com.hotelsystem.models.Room;
import com.hotelsystem.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Service
public class BookingServices {
    @Autowired
    private BookingRepository bookingRepository;

    public List<Room> checkAvailableRooms(Date fromDate, Date toDate, Integer guestsInteger) {
        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("JPA_F");

        EntityManager em = emf.createEntityManager();

        String jpql = "select r from Room r where r.availability = true and not exists " +
                "(select b from Booking b where r.id_room = b.room.id_room and ((:from between b.check_in and b.check_out) or (:to between b.check_in and b.check_out) or (:from < b.check_in and :to > b.check_out) or (r.occupancy < :guests)))";
        Query query = em.createQuery(jpql);
        query.setParameter("from", fromDate);
        query.setParameter("to", toDate);
        query.setParameter("guests", guestsInteger);

        return query.getResultList();
    }
}
