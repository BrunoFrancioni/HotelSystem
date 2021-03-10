package com.hotelsystem.controllers;

import com.hotelsystem.models.Booking;
import com.hotelsystem.services.CancellationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
                if (cancellationServices.createCancellation(booking))
                    return "redirect:/listBookingActive?success=true";
                else return "redirect:/listBookingActive?error_create=true";
            } else return "redirect:/listBookingActive?error_check_date=true";
        }catch (Exception e){
            System.out.println(e.getMessage());
            return "redirect:/listBookingActive?error_excep=true";
        }
    }

}
