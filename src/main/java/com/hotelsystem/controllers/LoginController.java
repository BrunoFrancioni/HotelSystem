package com.hotelsystem.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class LoginController {
    @GetMapping("/login")
    public String index() {
        return "login";
    }

    @PostMapping("/loginsuccess")
    public String login(@RequestParam String email, @RequestParam String password, Model model) {
        return "loginSuccessful";
    }



    @GetMapping("/userMan")
    public String userManag() {
        return "userList";
    }

    @GetMapping("/roomMan")
    public String roomManag() {
        return "roomList";
    }
}
