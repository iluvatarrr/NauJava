package com.dmitry.NauJava.dto;

import com.dmitry.NauJava.domain.user.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

/**
 * Dto пользователя.
 */
public record UserDto(
        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Invalid email format")
        String email,

        @NotNull(message = "Roles cannot be null")
        Set<Role> roles,

        @NotBlank(message = "Password cannot be blank")
        @Size(min = 4, message = "Password must be at least 4 characters")
        String password)
{}