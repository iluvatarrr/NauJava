package com.dmitry.NauJava.dto;

import com.dmitry.NauJava.domain.user.Role;
import java.util.Set;

/**
 * Dto пользователя.
 */
public record UserDto(
                String email,
                Set<Role> roles) {}
