package com.dmitry.NauJava.mapper;

import com.dmitry.NauJava.domain.user.User;
import com.dmitry.NauJava.dto.UserDto;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация маппера для пользователя
 */
@Service
public class UserMapper implements Mappable<User, UserDto> {

    @Override
    public User toEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }
        User user = new User();
        user.setEmail(dto.email());
        user.setRoles(dto.roles());
        user.setPassword(dto.password());
        return user;
    }

    @Override
    public UserDto toDto(User entity) {
        if (entity == null) {
            return null;
        }
        return new UserDto(
                entity.getEmail(),
                entity.getRoles(),
                entity.getPassword()
        );
    }

    @Override
    public List<UserDto> toDto(List<User> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> toEntity(List<UserDto> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}