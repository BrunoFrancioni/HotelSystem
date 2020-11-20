package com.hotelsystem.DAO;

import com.hotelsystem.models.Room;
import org.springframework.data.repository.CrudRepository;

public interface RoomDAOAPI extends CrudRepository<Room,Long> {

}
