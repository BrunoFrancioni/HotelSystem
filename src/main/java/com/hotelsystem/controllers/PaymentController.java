package com.hotelsystem.controllers;

import com.hotelsystem.services.BookingServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class PaymentController {

    @Autowired
    private BookingServices bookingServices;

    @GetMapping("/payment")
    public String payment(@RequestParam String id_room, Model model){

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attributes.getRequest().getSession(true);
        Date from_var = (Date) session.getAttribute("from_var");
        Date to_var = (Date) session.getAttribute("to_var");

        model.addAttribute("cost",bookingServices.getBookingCost(id_room,from_var,to_var));
        return "payment";
    }

    @PostMapping("/reserve")
    public String reserveRoom(@RequestParam String id_room) {
        // Reserve logic
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attributes.getRequest().getSession(true);
            Date from_var = (Date) session.getAttribute("from_var");
            Date to_var = (Date) session.getAttribute("to_var");

            bookingServices.saveBooking(id_room, from_var, to_var);
            return "redirect:/booking";

        } catch (Exception e) {
            System.out.println("NO LOGUEADO!!");
            return "redirect:/booking";
        }
    }
}
