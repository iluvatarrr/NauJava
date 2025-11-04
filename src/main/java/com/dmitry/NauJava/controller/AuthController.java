package com.dmitry.NauJava.controller;

import com.dmitry.NauJava.domain.user.User;
import com.dmitry.NauJava.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * AuthController - контроллер для работы с аутентификацией, регистрацией и выходом из приложения.
 */
@Controller
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Model model)  {
        try {
            userService.save(user);
            return "redirect:/login";
        }
        catch (Exception ex) {
            model.addAttribute("message", "User exists");
            return "registration";
        }
    }

    @GetMapping("/me")
    public String me(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        var username = auth.getPrincipal();
        model.addAttribute("username", username);
        return "me";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/api/v1/auth/login";
    }
}
