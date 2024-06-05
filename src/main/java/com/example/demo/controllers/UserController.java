package com.example.demo.controllers;

import com.example.demo.dto.UserCreateRequest;
import com.example.demo.dto.UserCreateResponse;
import com.example.demo.dto.UserDto;
import com.example.demo.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid UserCreateRequest userCreateRequest) {
        UserCreateResponse createdUser = userService.createUser(userCreateRequest);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    //public ResponseEntity<UserDto> deleteUser(@PathVariable("id") int id)
    @DeleteMapping("{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable("id") int id) {
        UserDto deletedUser = userService.deleteUser(id);
        return new ResponseEntity<>(deletedUser, HttpStatus.CREATED);
        // ResponseEntity.ok(deletedUser.getId())
    }

}
