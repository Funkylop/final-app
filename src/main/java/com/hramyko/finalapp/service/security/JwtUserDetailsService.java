package com.hramyko.finalapp.service.security;

import com.hramyko.finalapp.persistence.entity.User;
import com.hramyko.finalapp.service.UserService;
import com.hramyko.finalapp.service.security.jwt.JwtUser;
import com.hramyko.finalapp.service.security.jwt.JwtUserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("User with username: " + username + "not found");
        }

        return JwtUserFactory.create(user);
    }
}
