package com.samiulsifat.task_management.controller;

import com.samiulsifat.task_management.dto.LoginDto;
import com.samiulsifat.task_management.dto.RegisterDto;
import com.samiulsifat.task_management.model.User;
import com.samiulsifat.task_management.service.AuthenticationService;
import com.samiulsifat.task_management.service.JwtService;
import com.samiulsifat.task_management.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthController(UserService userService, JwtService jwtService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/{username}")
    public User getUserByUsername(@PathVariable("username") String username) {
        return userService.findByUsername(username);
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody RegisterDto user) {
        if (user.getRole() == null) {
            user.setRole("USER");
        }
        User registeredUser = authenticationService.signup(user);

        return "User registered successfully";
    }

    @PostMapping("/login")
    public String authenticate(@RequestBody LoginDto loginDto) {
        User authenticatedUser = authenticationService.authenticate(loginDto);
        if (authenticatedUser == null) {
            return null;
        }
        System.out.println("Authenticated User: " + authenticatedUser);

        return jwtService.generateToken(authenticatedUser.getUsername(), authenticatedUser.getRole());
    }
}
