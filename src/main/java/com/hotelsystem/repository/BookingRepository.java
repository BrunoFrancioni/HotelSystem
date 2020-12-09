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
            "(:from < b.check_in AND :to > b.check_out)))) ORDER BY r.occupancy,r.price")
    List<Room> checkAvailableRooms(@Param("from") Date fromDate, @Param("to") Date toDate, @Param("guests") Integer guestsInteger);

    @Query("SELECT r FROM Room r WHERE r.availability = true AND NOT EXISTS " +
            "(SELECT b FROM Booking b WHERE (r.occupancy < :guests) OR (r.id_room = b.room.id_room AND "+
            "((:from BETWEEN b.check_in AND b.check_out) OR "+
            "(:to BETWEEN b.check_in AND b.check_out) OR " +
            "(:from < b.check_in AND :to > b.check_out)))) ORDER BY r.occupancy,r.price")
    Page<Room> checkAvailableRoomsPageable(Pageable pageable, @Param("from") Date fromDate, @Param("to") Date toDate, @Param("guests") Integer guestsInteger);
}
