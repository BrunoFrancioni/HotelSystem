package com.hotelsystem.controllers;

import com.hotelsystem.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {

    @Autowired
    private UserServices userServices;

    @GetMapping({"/","/login"})
    public String index() {
        return "index";
    }

    @GetMapping("/menu")
    public String hello() {
        userServices.setUserSession();
        return "redirect:/booking";
    }

    @GetMapping({"/login_to_book"})
    public String login_to_book() {
        return "redirect:/";
    }

}
