package com.hotelsystem.controllers;

import com.hotelsystem.models.Booking;
import com.hotelsystem.services.CancellationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.text.ParseException;

@Controller
public class CancellationController {

    @Autowired
    private CancellationServices cancellationServices;

    @PostMapping("/cancelBooking")
    @Transactional
    public String cancelBooking(Model model, @RequestParam String id_booking) throws ParseException {

        try {
            Booking booking = cancellationServices.findBookingById(id_booking);

            if (cancellationServices.checkCancellationAvailable(booking.getCheck_in())) {
                if (cancellationServices.createCancellation(booking)){
                    String withdraw = cancellationServices.costWithdraw(id_booking);
                    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
                    HttpSession session = attributes.getRequest().getSession(true);
                    session.setAttribute("withdraw_msg",withdraw);
                    return "redirect:/listBookingActive?success=true";
                }
                else return "redirect:/listBookingActive?error_create=true";
            } else return "redirect:/listBookingActive?error_check_date=true";
        }catch (Exception e){
            System.out.println(e.getMessage());
            return "redirect:/listBookingActive?error_excep=true";
        }
    }

    @GetMapping("/details")
    public String seeDetails(@RequestParam String id_booking_det, Model model){
        Booking booking = cancellationServices.findBookingById(id_booking_det);
        model.addAttribute("booking_det_obj", booking);
        return "details";
    }

}
