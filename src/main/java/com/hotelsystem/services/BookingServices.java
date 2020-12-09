package com.hotelsystem.services;

import com.hotelsystem.models.Booking;
import com.hotelsystem.models.Room;
import com.hotelsystem.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class BookingServices {
    @Autowired
    private BookingRepository bookingRepository;

    public List<Room> roomsAvailable(Date from, Date to, Integer guests){
        return bookingRepository.checkAvailableRooms(from,to,guests);
    }

    public Page<Room> getAvailable(Pageable pageable, Date from, Date to, Integer guests) {
        return bookingRepository.checkAvailableRoomsPageable(pageable,from,to,guests);
    }

    public Double calculateBookingCost(Long id_room, Date from, Date to){
        return 0.0;
    }
}
