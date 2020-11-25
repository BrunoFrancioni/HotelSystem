package com.hotelsystem.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class LoginController {
    @RequestMapping("/login")
    public String index() {
        return "login";
    }

    @RequestMapping(value="/login", method= RequestMethod.POST)
    public String login(@RequestParam String email, @RequestParam String password, Model model) {
        return "booking";
    }

    @GetMapping("/user")
    public String userManag() {
        return "userList";
    }

    @GetMapping("/room")
    public String roomManag() {
        return "roomList";
    }
}
