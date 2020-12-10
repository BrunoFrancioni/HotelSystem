package com.hotelsystem.controllers;

import com.hotelsystem.models.Booking;
import com.hotelsystem.models.Room;
import com.hotelsystem.utils.CheckSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class BookingController {
    private DateParser dateParser;
    private CheckSession checkSession;

    @Autowired
    private BookingServices bookingServices;

    private Date FROM_VAR;
    private Date TO_VAR;
    private Integer GUESTS_VAR;

    @GetMapping("/booking")
    public String index(Model model) {
        Date today = new Date();
        String dateFormat = "yyyy-MM-dd";
        SimpleDateFormat date = new SimpleDateFormat(dateFormat);
        String todayString = date.format(today);

        String tomorrow = date.format(new Date(today.getTime() + (1000 * 60 * 60 * 24)));

        model.addAttribute("dateToday", todayString);
        model.addAttribute("dateTomorrow", tomorrow);

        return "checkBooking";
    }

    @PostMapping("/booking/check")
    public String checkAvailable(@RequestParam Map<String,Object> params, @RequestParam String from, @RequestParam String to, @RequestParam String guests, Model model) throws ParseException {

        Date fromDate = DateParser.parseDate(from);
        Date toDate = DateParser.parseDate(to);
        Integer guestsInteger = Integer.parseInt(guests);
        FROM_VAR = fromDate;
        TO_VAR = toDate;
        GUESTS_VAR = guestsInteger;

        if(fromDate.compareTo(toDate) == 0 || fromDate.compareTo(toDate) > 0) {
            return "redirect:/booking?error=true";
        }

        int page = params.get("page") != null ? (Integer.valueOf(params.get("page").toString()) - 1) : 0;

        PageRequest pageRequest = PageRequest.of(page, 10);

        Page<Room> pageRoom = bookingServices.getAvailable(pageRequest,fromDate,toDate,guestsInteger);

        int totalPage = pageRoom.getTotalPages();
        if(totalPage > 0) {
            List<Integer> pages = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
            model.addAttribute("pages", pages);
        }

        model.addAttribute("available", pageRoom.getContent());
        model.addAttribute("current", page + 1);
        model.addAttribute("next", page + 2);
        model.addAttribute("prev", page);
        model.addAttribute("last", totalPage);

        return "booking";
    }

    @GetMapping("/booking/checkPage")
    public String checkAvailable(@RequestParam Map<String,Object> params, Model model) throws ParseException {

        int page = params.get("page") != null ? (Integer.valueOf(params.get("page").toString()) - 1) : 0;

        PageRequest pageRequest = PageRequest.of(page, 10);

        Page<Room> pageRoom = bookingServices.getAvailable(pageRequest,FROM_VAR,TO_VAR,GUESTS_VAR);

        int totalPage = pageRoom.getTotalPages();
        if(totalPage > 0) {
            List<Integer> pages = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
            model.addAttribute("pages", pages);
        }

        model.addAttribute("available", pageRoom.getContent());
        model.addAttribute("current", page + 1);
        model.addAttribute("next", page + 2);
        model.addAttribute("prev", page);
        model.addAttribute("last", totalPage);

        return "booking";
    }


    @PostMapping("/reserve")
    public String reserveRoom(@RequestParam String id_room, Model model) {
        if(!checkSession.check()){
            return "redirect:/";
        }

        try {
            bookingServices.saveBooking(id_room, FROM_VAR, TO_VAR);
                return "redirect:/booking";
        } catch (Exception e){
            System.out.println(e.getMessage());
            return "redirect:/";
        }
    }
}