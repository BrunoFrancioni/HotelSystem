package com.hotelsystem.controllers;

import com.hotelsystem.models.Room;
import com.hotelsystem.models.User;
import com.hotelsystem.services.UserServices;
import com.hotelsystem.utils.DateParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.util.Date;

@Controller
public class UserController {

    @Autowired
    UserServices userServices;

    @GetMapping("/register")
    public String registerPage(Model model) {
        return "userRegister";
    }

    @PostMapping("/userSave")
    public String save(@RequestParam String email,@RequestParam String password,@RequestParam String first_name,@RequestParam String last_name,@RequestParam String birthdate,@RequestParam String nationality, Model model) throws ParseException {
        User user = new User();
        Date birthdateSQL = DateParser.parseDate(birthdate);
        user.setBirthdate(birthdateSQL);
        user.setEmail(email);
        user.setPassword(password);
        user.setFirst_name(first_name);
        user.setLast_name(last_name);
        user.setNationality(nationality);

        boolean result = userServices.createUser(user);

        if(result) {
            return "redirect:/";
        }

        return "redirect:/register?error=true";
    }

    @GetMapping("/setting")
    public String getUserSetting(@RequestParam String id_user, Model model) {
        User user = userServices.getUserById(Long.parseLong(id_user));

        model.addAttribute("user", user);

        return "settings";
    }

    @PostMapping("/setting")
    public String saveUserSettings() {
        return "/";
    }
}
