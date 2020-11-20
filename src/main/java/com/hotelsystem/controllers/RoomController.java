package com.hotelsystem.controllers;

import com.hotelsystem.models.Room;
import com.hotelsystem.services.RoomServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RoomController {
    @Autowired
    private RoomServices roomServices;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("list", roomServices.findAll());

        return "roomList";
    }

    @GetMapping("/save")
    public String showSave(Model model) {
        model.addAttribute("room", new Room());

        return "roomSave";
    }

    @PostMapping("/save")
    public String save(Room room, Model model) {
        boolean result = roomServices.createRoom(room);

        if(result) {
            return "redirect:/";
        }

        return "/save/error";
    }

    @GetMapping("update/{id}")
    public String showUpdate(@PathVariable("id") Long id, Model model) {
        model.addAttribute("room", roomServices.getRoomById(id));

        return "/roomUpdate";
    }

    @PostMapping("/update")
    public String update(Room room, Model model){
        boolean result = roomServices.updateRoom(room);

        if(result) {
            return "redirect:/";
        }

        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        boolean result = roomServices.deleteRoom(id);

        if(result) {
            return "redirect:/";
        }

        return "/delete/error";
    }
}
