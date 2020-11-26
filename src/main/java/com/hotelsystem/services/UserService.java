package com.hotelsystem.services;

import com.hotelsystem.models.User;
import com.hotelsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
    }

    public User updateUser(User user, Long id) {
        return userRepository.findById(id)
                .map(u -> {
                    u.setEmail(user.getEmail());
                    u.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
                    u.setFirst_name(user.getFirst_name());
                    u.setLast_name(user.getLast_name());
                    u.setBirthdate(user.getBirthdate());
                    u.setNationality(user.getNationality());
                    return userRepository.save(u);
                })
                .orElseGet(() -> {
                    return userRepository.save(user);
                });
    }

    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

    public User findUserByEmail(String email) throws UsernameNotFoundException {
        try {
            return userRepository.findUserByEmail(email);
        } catch(UsernameNotFoundException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

}
