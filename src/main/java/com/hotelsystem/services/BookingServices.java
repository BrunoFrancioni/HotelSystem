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


    public Optional<Room> findRoomById(Long id_room){
        return roomRepository.findById(id_room);
    }

    public List<Room> roomsAvailable(Date from, Date to, Integer guests){
        return bookingRepository.checkAvailableRooms(from,to,guests);
    }

    public Page<Room> getAvailable(Pageable pageable, Date from, Date to, Integer guests) {
        return bookingRepository.checkAvailableRoomsPageable(from,to,guests,pageable);
    }

    public Optional<Booking> findBookingById(Long id_booking){
        return bookingRepository.findById(id_booking);
    }

    @Transactional
    public boolean saveBooking(Booking booking){        //true si se guardo, false si no.
        try{
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attributes.getRequest().getSession(true);

            //check in and check out
            Date check_in = (Date) session.getAttribute("from_var");
            Date check_out = (Date) session.getAttribute("to_var");

            booking.setCheck_in(check_in);
            booking.setCheck_out(check_out);

            //Created_at
            LocalDate ld = LocalDate.now();
            String ldString = String.valueOf(ld);
            booking.setCreated_at(new DateParser().parseDate(ldString));

            //user

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            Optional<User> userRes = userRepository.findByEmail(email);
            booking.setGuest(userRes.get());

            List<Booking> list_check = bookingRepository.finalCheckAvailableBooking(check_in,check_out,booking.getGuest().getId());

            if(list_check.isEmpty()){
                bookingRepository.save(booking);
                System.out.println("Booking save");
                return true;
            } else{
                System.out.println("Ya existe un booking");
                return false;
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Double calculateBookingCost(Date from, Date to, Double base){

        LocalDate check_inLD = from.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate check_outLD = to.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Long days = DAYS.between(check_inLD,check_outLD);
        return days*base;
    }

    public Double getBookingCostBase(String id_room, Date from, Date to){
        Double base = roomRepository.getOne(Long.parseLong(id_room)).getPrice();
        return base;
    }

    public Double calculateTotalCost(Long id_room, String cancelacion, String cochera, String desayuno){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attributes.getRequest().getSession(true);
        Date from_var = (Date) session.getAttribute("from_var");
        Date to_var = (Date) session.getAttribute("to_var");
        LocalDate check_inLD = from_var.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate check_outLD = to_var.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Long days = DAYS.between(check_inLD,check_outLD);

        Double precio_sin_opciones = calculateBookingCost(from_var, to_var, getBookingCostBase(String.valueOf(id_room),from_var,to_var));
        if(cancelacion != null){
            precio_sin_opciones += 300.0;
        }
        if(cochera != null){
            precio_sin_opciones += days*300;
        }
        if(desayuno != null){
            precio_sin_opciones += days*100;
        }
        return precio_sin_opciones;
    }

    public List<Booking> bookingsActive(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attributes.getRequest().getSession(true);
        User user = (User) session.getAttribute("usersession");

        return bookingRepository.allBookingsUser(user.getId());
    }

    public Page<Booking> getBookingsActivePage (Pageable pageable) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attributes.getRequest().getSession(true);
        Optional<User> opt_user = (Optional<User>) session.getAttribute("usersession");
        User user = opt_user.get();
        return bookingRepository.allBookingsUserPageable(user.getId(),pageable);
    }

}
