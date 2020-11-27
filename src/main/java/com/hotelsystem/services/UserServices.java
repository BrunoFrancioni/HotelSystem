package com.hotelsystem.services;

import com.hotelsystem.models.Authority;
import com.hotelsystem.models.Room;
import com.hotelsystem.models.User;
import com.hotelsystem.repository.RoomRepository;
import com.hotelsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServices {
    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Autowired
    private EntityManager em;

    public boolean createUser(User user) {

        Query query = em.createNamedQuery("Authority.user");
        List<Authority> list = query.getResultList();
        Set<Authority> authoritySet = new HashSet<Authority>(list);
        try {
            user.setAuthority(authoritySet);
            String passwordEncoder = new BCryptPasswordEncoder().encode(user.getPassword());
            user.setPassword(passwordEncoder);
            userRepository.save(user);

            return true;
        } catch(Exception e) {
            System.out.println(e);

            return false;
        }
    }

    public User getUserById(Long id) {
        return userRepository.getOne(id);
    }

    public boolean updateUSer(User user) {
        try {
            userRepository.updateUser(user.getFirst_name(), user.getNationality(), user.getLast_name());

            return true;
        } catch(Exception e) {
            System.out.println(e);

            return false;
        }
    }

    public boolean deleteUser(Long id) {
        try {
            userRepository.deleteById(id);

            return true;
        } catch(Exception e) {
            System.out.println(e);

            return false;
        }
    }
}