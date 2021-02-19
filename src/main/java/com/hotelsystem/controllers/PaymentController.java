package com.hotelsystem.controllers;

import com.hotelsystem.dtos.BookingDetailsDTO;
import com.hotelsystem.models.Booking;
import com.hotelsystem.models.Room;
import com.hotelsystem.services.BookingServices;
import com.hotelsystem.services.RoomServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Optional;

@Controller
public class PaymentController {

    @Autowired
    private BookingServices bookingServices;

    @Autowired
    private RoomServices roomServices;

    @GetMapping("/bookingDetails")
    public String bookingDetails(@RequestParam String id_room, Model model) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attributes.getRequest().getSession(true);
        Date from_var = (Date) session.getAttribute("from_var");
        Date to_var = (Date) session.getAttribute("to_var");

        int days = (int) ((to_var.getTime() - from_var.getTime()) / 86400000);

        model.addAttribute("cost_wout_extras",bookingServices.calculateBookingCost(from_var,to_var,bookingServices.getBookingCostBase(id_room,from_var,to_var)));
        model.addAttribute("room", roomServices.getRoomById(Long.parseLong(id_room)));
        session.setAttribute("id_room", id_room);
        model.addAttribute("days", days);
        model.addAttribute("bookingDetailsDTO", new BookingDetailsDTO());

        return "bookingDetails";
    }

    @GetMapping("/payment")
    public String payment(@ModelAttribute BookingDetailsDTO bookingDetailsDTO, Model model){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attributes.getRequest().getSession(true);
        Date from_var = (Date) session.getAttribute("from_var");
        Date to_var = (Date) session.getAttribute("to_var");
        String id_room = (String) session.getAttribute("id_room");
        Long id_roomLong = Long.parseLong(id_room);

        Booking booking = new Booking();

        Optional<Room> optRoom = bookingServices.findRoomById(id_roomLong);
        booking.setRoom(optRoom.get());

        if(bookingDetailsDTO.getCancelacion() != null)
            booking.setFree_cancellation(true);
        else booking.setFree_cancellation(false);
        if(bookingDetailsDTO.getCochera() != null)
            booking.setParking(true);
        else booking.setParking(false);
        if(bookingDetailsDTO.getDesayuno() != null)
            booking.setBreakfast_included(true);
        else booking.setBreakfast_included(false);

        booking.setCost(bookingServices.calculateTotalCost(id_roomLong, bookingDetailsDTO.getCancelacion(), bookingDetailsDTO.getCochera(), bookingDetailsDTO.getDesayuno()));

        session.setAttribute("booking_to_reserve", booking);

        return "payment";
    }

    @PostMapping("/reserve")
    public String reserveRoom(Model model) {
        // Reserve logic
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attributes.getRequest().getSession(true);
            Booking booking = (Booking) session.getAttribute("booking_to_reserve");
            if(bookingServices.saveBooking(booking)){
                return "redirect:/booking";
            } else return "redirect:/";


        } catch (Exception e) {
            System.out.println(e.getMessage() + "NO LOGUEADO!!");
            return "redirect:/";
        }
    }
}
