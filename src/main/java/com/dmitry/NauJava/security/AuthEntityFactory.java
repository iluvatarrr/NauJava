package com.dmitry.NauJava.security;

import com.dmitry.NauJava.domain.user.Role;
import com.dmitry.NauJava.domain.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AuthEntityFactory - фабрика для создание AuthEntity из класса пользователя.
 */
public class AuthEntityFactory {
    public static AuthEntity createAuthEntity(User user) {
        return new AuthEntity(user.getEmail(), user.getPassword(),
                mapToGrantedAuthorities(new ArrayList<>(user.getRoles())));
    }

    public static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toList());
    }
}