package com.example.demo.services;
import com.example.demo.model.User;
import com.example.demo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(User user) {
        /*if (userRepository.existsBy()) {
            throw new IllegalArgumentException("User with username '" + user.getUsername() + "' already exists");
        }*/

        return userRepository.save(user);
    }
}
