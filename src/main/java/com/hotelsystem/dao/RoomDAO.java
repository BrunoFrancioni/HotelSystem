package com.hotelsystem.dao;

import com.hotelsystem.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomDAO extends JpaRepository<Room,Long>{

}
