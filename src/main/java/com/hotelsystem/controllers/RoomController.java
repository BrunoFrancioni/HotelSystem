package com.hotelsystem.controllers;

import com.hotelsystem.models.Room;
import com.hotelsystem.services.api.RoomServiceAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RoomController {
    @Autowired
    private RoomServiceAPI roomServiceAPI;


}
