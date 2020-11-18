package com.hotelsystem.controllers;

import com.hotelsystem.dao.RoomDAO;
import com.hotelsystem.models.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("room")
public class RoomController {
    @Autowired
    private RoomDAO roomDAO;

    @PostMapping("/save")
    public void save(@RequestBody Room room) {
        roomDAO.save(room);
    }

    @GetMapping("/list")
    public List<Room> listAll() {
        return roomDAO.findAll();
    }
}
