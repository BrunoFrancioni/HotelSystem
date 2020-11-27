package com.hotelsystem.repository;


import com.hotelsystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    public Optional<User> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("update User u set u.first_name = :first_name, u.nationality = :nationality, u.last_name = :last_name")
    void updateUser(@Param("first_name") String first_name, @Param("nationality") String nationality, @Param("last_name") String last_name);
}
