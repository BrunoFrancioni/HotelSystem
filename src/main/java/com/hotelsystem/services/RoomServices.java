package com.hotelsystem.services;

import com.hotelsystem.models.Room;
import com.hotelsystem.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RoomServices {
    @Autowired
    private RoomRepository roomRepository;

    public boolean save(Room room) {
        try {
            roomRepository.save(room);

            return true;
        } catch(Exception e) {
            System.out.println(e);

            return false;
        }

    }
}
