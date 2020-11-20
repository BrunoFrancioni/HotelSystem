package com.hotelsystem.services.impl;

import com.hotelsystem.DAO.RoomDAOAPI;
import com.hotelsystem.commons.GenericServiceImpl;
import com.hotelsystem.models.Room;
import com.hotelsystem.services.api.RoomServiceAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class RoomServiceImpl extends GenericServiceImpl<Room, Long> implements RoomServiceAPI {
    @Autowired
    private RoomDAOAPI roomDAOAPI;

    @Override
    public CrudRepository<Room, Long> getDao() {
        return roomDAOAPI;
    }
}
