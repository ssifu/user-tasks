package com.samiulsifat.task_management.controller;

import com.samiulsifat.task_management.model.User;
import com.samiulsifat.task_management.service.AuthenticationService;
import com.samiulsifat.task_management.service.JwtService;
import com.samiulsifat.task_management.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public UserController(UserService userService, JwtService jwtService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/")
    public List<User> getAllUsers() {
        return userService.findAllUser();
    }
}
