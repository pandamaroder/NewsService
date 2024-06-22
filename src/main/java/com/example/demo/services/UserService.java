package com.example.demo.services;

import com.example.demo.dto.UserCreateRequest;
import com.example.demo.dto.UserCreateResponse;
import com.example.demo.dto.UserDto;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.model.User;
import com.example.demo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserCreateResponse createUser(UserCreateRequest userCreateRequest) {

        final User user = User.builder()
            .username(userCreateRequest.username())
            .createdAt(LocalDateTime.now(ZoneId.of("Europe/Moscow")))
            .build();
        final User savedUser = userRepository.save(user);
        return new UserCreateResponse(savedUser.getId(), savedUser.getUsername());
    }

    @Transactional
    public UserDto deleteUser(long userId) {

        final User user = userRepository.getById(userId);
        userRepository.delete(user);
        return UserDto.builder()
            .username(user.getUsername())
            .id(user.getId())
            .build();
    }

    public User getCurrentUser(HttpServletRequest request) {
        final String userId = extractUserIdFromRequest(request);
        return userRepository.findById(Long.parseLong(userId))
            .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
    }

    private String extractUserIdFromRequest(HttpServletRequest request) {
        request.getAuthType();
        return "extractedUserId";
    }
}
