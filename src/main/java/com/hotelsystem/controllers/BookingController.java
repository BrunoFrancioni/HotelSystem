package com.hotelsystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.hotelsystem.utils.DateParser;
import com.hotelsystem.services.BookingServices;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class BookingController {
    private DateParser dateParser;

    @Autowired
    private BookingServices bookingServices;

    @GetMapping("/booking")
    public String index(Model model) {
        Date today = new Date();
        String dateFormat = "yyyy-MM-dd";
        SimpleDateFormat date = new SimpleDateFormat(dateFormat);
        String todayString = date.format(today);

        Integer tomorrowDate = Integer.parseInt(todayString.substring(8)) + 1;
        String tomorrow = todayString.substring(0,8) + tomorrowDate;

        model.addAttribute("dateToday", todayString);
        model.addAttribute("dateTomorrow", tomorrow);

        return "checkBooking";
    }

    @PostMapping("/booking/check")
    public String checkAvailables(@RequestParam String from, @RequestParam String to, @RequestParam String guests, Model model) throws ParseException {
        Date fromDate = DateParser.parseDate(from);
        Date toDate = DateParser.parseDate(to);
        Integer guestsInteger = Integer.parseInt(guests);

        model.addAttribute("availabilities", bookingServices.checkAvailableRooms(fromDate, toDate, guestsInteger));

        return "booking";
    }
}
