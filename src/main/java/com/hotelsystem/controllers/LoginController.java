package com.hotelsystem.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping({"/","/login"})
    public String index() {
        return "index";
    }

    @GetMapping("/menu")
    public String hello() {
        return "menu";
    }

}
