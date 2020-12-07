package com.hotelsystem.services;

import com.hotelsystem.models.Booking;
import com.hotelsystem.models.Room;
import com.hotelsystem.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
}
