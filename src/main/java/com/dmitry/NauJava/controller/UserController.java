package com.dmitry.NauJava.controller;

import com.dmitry.NauJava.domain.user.User;
import com.dmitry.NauJava.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Контроллер пользователей.
 * Работает напрямую с репозиторием по условию задания.
 * Позволяет выводить список пользователей
 */
@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/list")
    public String getUsers(Model model) {
        Iterable<User> products = userRepository.findAll();
        model.addAttribute("users", products);
        return "index";
    }
}