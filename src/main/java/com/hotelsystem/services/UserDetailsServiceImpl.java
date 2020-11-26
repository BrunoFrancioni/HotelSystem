package com.hotelsystem.services;

import ch.qos.logback.core.util.SystemInfo;
import com.hotelsystem.models.Authority;
import com.hotelsystem.models.User;
import com.hotelsystem.repository.UserRepository;
import com.hotelsystem.utils.UserLoggedIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername (String email) throws UsernameNotFoundException {
        User appUser;
        //Buscar el usuario con el repositorio y si no existe lanzar una exepcion
        try {
            appUser =
                    userService.findUserByEmail(email);
        } catch (Exception e){
            System.out.println(e.getMessage()); //"Email not registered"
            return null;
        }

        //Mapear nuestra lista de Authority con la de spring security
        List grantList = new ArrayList();
        for (Authority authority: appUser.getAuthorities()) {
            // ROLE_USER, ROLE_ADMIN,..
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getAuthority());
            grantList.add(grantedAuthority);
        }

        //Crear El objeto UserLoggedIn que va a ir en sesion y retornarlo.
        UserDetails user = (UserDetails) new User(appUser.getEmail(), appUser.getPassword(), grantList);
        return user;
    }


}