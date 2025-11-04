package com.dmitry.NauJava.service;

import com.dmitry.NauJava.domain.user.User;

/**
 * CRUD интерфейс для взаимодействия с пользователями
 */
public interface UserService {
    User save(User user);
    User findByEmail(String email);
}
