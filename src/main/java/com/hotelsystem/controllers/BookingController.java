package com.hotelsystem.controllers;

import com.hotelsystem.models.Room;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
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

    @Autowired
    private BookingServices bookingServices;


    @GetMapping("/booking")
    public String index(Model model) {
        Date today = new Date();
        String dateFormat = "yyyy-MM-dd";
        SimpleDateFormat date = new SimpleDateFormat(dateFormat);
        String todayString = date.format(today);

        String tomorrow = date.format(new Date(today.getTime() + (1000 * 60 * 60 * 24)));

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attributes.getRequest().getSession(true);
        if(session.getAttribute("from_var") != null){

            String today_from_session = date.format(session.getAttribute("from_var"));
            String tomorrow_from_session = date.format(session.getAttribute("to_var"));
            String guests_session = "" + session.getAttribute("guests_var");
            model.addAttribute("dateToday_session", today_from_session);
            model.addAttribute("dateTomorrow_session", tomorrow_from_session);
            model.addAttribute("guests_session", guests_session);
        } else{
            model.addAttribute("dateToday", todayString);
            model.addAttribute("dateTomorrow", tomorrow);
        }

        return "checkBooking";
    }

    @PostMapping("/booking/check")
    public String checkAvailable(@RequestParam Map<String,Object> params, @RequestParam String from, @RequestParam String to, @RequestParam String guests, Model model) throws ParseException {

        Date fromDate = DateParser.parseDate(from);
        Date toDate = DateParser.parseDate(to);
        Integer guestsInteger = Integer.parseInt(guests);

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attributes.getRequest().getSession(true);
        session.setAttribute("from_var", fromDate);
        session.setAttribute("to_var", toDate);
        session.setAttribute("guests_var", guestsInteger);

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

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attributes.getRequest().getSession(true);

        Date from_var = (Date) session.getAttribute("from_var");
        Date to_var = (Date) session.getAttribute("to_var");
        Integer guests_var = (Integer) session.getAttribute("guests_var");

        Page<Room> pageRoom = bookingServices.getAvailable(pageRequest,from_var,to_var,guests_var);

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
<<<<<<< HEAD

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
=======
>>>>>>> f337b4b92613abe59ef942a38be632245a5bfd4f
}