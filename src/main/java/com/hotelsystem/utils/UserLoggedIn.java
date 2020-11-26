package com.hotelsystem.utils;

import com.hotelsystem.models.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserLoggedIn extends User {

    private com.hotelsystem.models.User user;

    public UserLoggedIn(String email, String password, Collection<? extends GrantedAuthority> authorities, com.hotelsystem.models.User user) {
        super(email, password, authorities);
        this.user = user;
    }

    public com.hotelsystem.models.User getUser() {
        return user;
    }

    public void setUser(com.hotelsystem.models.User user) {
        this.user = user;
    }
}
