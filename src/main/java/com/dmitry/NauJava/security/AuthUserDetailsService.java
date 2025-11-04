package com.dmitry.NauJava.security;

import com.dmitry.NauJava.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * AuthUserDetailsService - реализация UserDetailsService.
 */
@Service
public class AuthUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Autowired
    public AuthUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userService.findByEmail(username);

        return AuthEntityFactory.createAuthEntity(user);
    }
}
