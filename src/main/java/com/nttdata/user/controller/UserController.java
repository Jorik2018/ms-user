package com.nttdata.user.controller;

import com.nttdata.user.model.*;
import com.nttdata.user.model.entity.Phone;
import com.nttdata.user.model.entity.User;
import com.nttdata.user.service.UserService;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest userRequest) {
        User user = new User();
        user.setName(userRequest.getName());
        user.setPassword(userRequest.getPassword());
        user.setEmail(userRequest.getEmail());
        user.setPhones(userRequest.getPhones());
        User savedUser = userService.saveUser(user);
        return new ResponseEntity<>(UserResponse.builder()
            .id(savedUser.getId())
            .created(savedUser.getCreated())
            .modified(savedUser.getModified())
            .lastLogin(savedUser.getLastLogin())
            .token(savedUser.getToken())
            .isActive(savedUser.isActive())
            .build(), HttpStatus.CREATED);
    }

}
