package com.dmitry.NauJava.service.impl;

import com.dmitry.NauJava.domain.exception.ResourceNotFoundException;
import com.dmitry.NauJava.domain.user.Role;
import com.dmitry.NauJava.domain.user.User;
import com.dmitry.NauJava.repository.UserRepository;
import com.dmitry.NauJava.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * UserServiceImpl - реализация UserService.
 * Способен выполнять функционал нахождения по email и сохранения пользователя.
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User save(User user) {
        user.setEnabled(false);
        if (user.getRoles().isEmpty()) {
            user.getRoles().add(Role.USER);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new ResourceNotFoundException("Email is null or empty");
        }
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("User with email: %s, not found", email)));
    }

    @Override
    public Long count() {
        return userRepository.count();
    }
}
