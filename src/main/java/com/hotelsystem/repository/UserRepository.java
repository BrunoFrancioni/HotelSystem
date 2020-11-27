package com.hotelsystem.repository;


import com.hotelsystem.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    public Optional<User> findByEmail(String email);
}
