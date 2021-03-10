package com.hotelsystem.repository;

import com.hotelsystem.models.Booking;
import com.hotelsystem.models.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {

    @Query("SELECT r FROM Room r WHERE r.availability = true AND NOT EXISTS " +
            "(SELECT b FROM Booking b WHERE (r.occupancy < :guests) OR (r.id_room = b.room.id_room AND "+
            "((:from BETWEEN b.check_in AND b.check_out) OR "+
            "(:to BETWEEN b.check_in AND b.check_out) OR " +
            "(:from <= b.check_in AND :to >= b.check_out)))) ORDER BY r.occupancy,r.price")
    List<Room> checkAvailableRooms(@Param("from") Date fromDate, @Param("to") Date toDate, @Param("guests") Integer guestsInteger);

    @Query("SELECT r FROM Room r WHERE r.availability = true AND NOT EXISTS " +
            "(SELECT b FROM Booking b WHERE (r.occupancy < :guests) OR (r.id_room = b.room.id_room AND "+
            "((:from BETWEEN b.check_in AND b.check_out) OR "+
            "(:to BETWEEN b.check_in AND b.check_out) OR " +
            "(:from <= b.check_in AND :to >= b.check_out)))) ORDER BY r.occupancy,r.price")
    Page<Room> checkAvailableRoomsPageable(Pageable pageable, @Param("from") Date fromDate, @Param("to") Date toDate, @Param("guests") Integer guestsInteger);

    @Query("SELECT b FROM Booking b WHERE b.room.id_room = :id_room AND " +
            "((:from BETWEEN b.check_in AND b.check_out) OR "+
            "(:to BETWEEN b.check_in AND b.check_out) OR " +
            "(:from < b.check_in AND :to > b.check_out))")
    List<Booking> finalCheckAvailableBooking(@Param("from") Date fromDate, @Param("to") Date toDate, @Param("id_room") Long id_room);

    @Query(value="select * FROM booking WHERE check_in > CURDATE() AND user = ?1 AND NOT EXISTS(SELECT * FROM cancellation WHERE cancellation.id_booking = booking.id_booking)"
            , nativeQuery = true)
    List<Booking> allBookingsUser(@Param("id_user") Long id_user);

    @Query(value="select * FROM booking WHERE check_in > CURDATE() AND guest = ?1 AND NOT EXISTS(SELECT * FROM cancellation WHERE cancellation.id_booking = booking.id_booking)",
            countQuery = "select count(*) FROM booking WHERE check_in > CURDATE() AND guest = ?1 AND NOT EXISTS(SELECT * FROM cancellation WHERE cancellation.id_booking = booking.id_booking)"
            ,nativeQuery = true)
    Page<Booking> allBookingsUserPageable(@Param("id_user") Long id_user, Pageable pageable);

}
