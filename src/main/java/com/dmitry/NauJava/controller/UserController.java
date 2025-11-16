package com.dmitry.NauJava.controller;

import com.dmitry.NauJava.dto.UserDto;
import com.dmitry.NauJava.mapper.UserMapper;
import com.dmitry.NauJava.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер пользователей.
 */
@RestController
@RequestMapping("/api/v1/users")
@Validated
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public UserDto createUser(@RequestBody @Valid UserDto userDto) {
        var user = userMapper.toEntity(userDto);
        return userMapper.toDto(userService.save(user));
    }
}