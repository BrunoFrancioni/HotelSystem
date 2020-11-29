package com.hotelsystem.services;

import com.hotelsystem.models.Room;
import com.hotelsystem.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Service
public class BookingServices {
    @Autowired
    private BookingRepository bookingRepository;

    public List<Room> roomsAvailable(Date from, Date to, Integer guests){
        return bookingRepository.checkAvailableRooms(from,to,guests);
    }
}
