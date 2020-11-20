package com.hotelsystem.services;

import com.hotelsystem.models.Room;
import com.hotelsystem.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RoomServices {
    @Autowired
    private RoomRepository roomRepository;

    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    public boolean createRoom(Room room) {
        try {
            roomRepository.save(room);

            return true;
        } catch(Exception e) {
            System.out.println(e);

            return false;
        }
    }

    public Room getRoomById(Long id) {
        return roomRepository.getOne(id);
    }

    public boolean updateRoom(Room room) {
        try {
            roomRepository.updateRoom(room.getName(), room.getPrice(), room.getOccupancy(), room.getAvailability(), room.getFacilities());

            return true;
        } catch(Exception e) {
            System.out.println(e);

            return false;
        }
    }

    public boolean deleteRoom(Long id) {
        try {
            roomRepository.deleteById(id);

            return true;
        } catch(Exception e) {
            System.out.println(e);

            return false;
        }
    }
}
