package com.dmitry.NauJava.service;

import com.dmitry.NauJava.domain.user.User;

/**
 * Интерфейс для взаимодействия с пользователями
 */
public interface UserService {
    Long count();
    User save(User user);
    User findByEmail(String email);
}
