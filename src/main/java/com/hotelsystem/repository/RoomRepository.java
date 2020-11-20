package com.hotelsystem.repository;

import com.hotelsystem.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface RoomRepository extends JpaRepository<Room,Long> {
    @Modifying
    @Transactional
    @Query("update Room r set r.name = :name, r.price = :price, r.occupancy = :occupancy, r.availability = :availability, r.facilities = :facilities")
    void updateRoom(@Param("name") String name, @Param("price") Double price, @Param("occupancy") Integer occupancy, @Param("availability") Boolean availability, @Param("facilities") String facilities);
}
