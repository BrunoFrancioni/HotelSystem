package com.hotelsystem.services;

import com.hotelsystem.models.Booking;
import com.hotelsystem.models.Room;
import com.hotelsystem.models.User;
import com.hotelsystem.repository.BookingRepository;
import com.hotelsystem.repository.RoomRepository;
import com.hotelsystem.repository.UserRepository;
import com.hotelsystem.utils.DateParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@Transactional
public class BookingServices {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Room> roomsAvailable(Date from, Date to, Integer guests){
        return bookingRepository.checkAvailableRooms(from,to,guests);
    }

    public Page<Room> getAvailable(Pageable pageable, Date from, Date to, Integer guests) {
        return bookingRepository.checkAvailableRoomsPageable(pageable,from,to,guests);
    }

    public Double calculateBookingCost(Date from, Date to, Double base){

        LocalDate check_inLD = from.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate check_outLD = to.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Long days = DAYS.between(check_inLD,check_outLD);
        return days*base;
    }

    @Transactional
    public void saveBooking(String id_room, Date check_in, Date check_out){
        try{
            Booking booking = new Booking();
            booking.setCheck_in(check_in);
            booking.setCheck_out(check_out);
            booking.setBreakfast_included(false);
            booking.setParking(false);
            booking.setFree_cancellation(false);

            //Created_at
            LocalDate ld = LocalDate.now();
            String ldString = String.valueOf(ld);
            booking.setCreated_at(new DateParser().parseDate(ldString));
            //room
            Long idRoom = Long.parseLong(id_room);
            Optional<Room> roomRes = roomRepository.findById(idRoom);
            booking.setRoom(roomRes.get());             //check
            //user
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attributes.getRequest().getSession(true);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            Optional<User> userRes = userRepository.findByEmail(email);
            booking.setGuest(userRes.get());

            //cost
            Double baseCost = roomRepository.findById(idRoom).get().getPrice();
            booking.setCost(calculateBookingCost(check_in,check_out,baseCost));

            bookingRepository.save(booking);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }



    }

    public Double getBookingCost(String id_room, Date from, Date to){
        LocalDate check_inLD = from.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate check_outLD = to.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Long days = DAYS.between(check_inLD,check_outLD);
        Double base = roomRepository.getOne(Long.parseLong(id_room)).getPrice();
        return days*base;
    }
}
