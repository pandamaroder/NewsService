package com.example.demo.services;

import com.example.demo.dto.UserCreateRequest;
import com.example.demo.dto.UserCreateResponse;
import com.example.demo.dto.UserDto;
import com.example.demo.model.User;
import com.example.demo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserCreateResponse createUser(UserCreateRequest userCreateRequest) {

        User user = User.builder()
            .username(userCreateRequest.username())
            .createdAt(LocalDateTime.now(ZoneId.of("Europe/Moscow")))
            .build();
        User savedUser = userRepository.save(user);
        return new UserCreateResponse(savedUser.getId(), savedUser.getUsername());
    }

    @Transactional
    public UserDto deleteUser(long userId) {

        User user = userRepository.getById(userId);
        userRepository.delete(user);
        return UserDto.builder()
            .username(user.getUsername())
            .id(user.getId())
            .build();
    }
}
