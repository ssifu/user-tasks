package com.samiulsifat.task_management.controller;

import com.samiulsifat.task_management.dto.LoginDto;
import com.samiulsifat.task_management.dto.RegisterDto;
import com.samiulsifat.task_management.model.User;
import com.samiulsifat.task_management.service.AuthenticationService;
import com.samiulsifat.task_management.service.JwtService;
import com.samiulsifat.task_management.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.samiulsifat.task_management.model.Role.USER;

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
        if (user.getRoles() == null) {
            user.setRoles(Set.of(USER));
        } else {
            user.getRoles().add(USER);
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

        return jwtService.generateToken(authenticatedUser.getUsername());
    }
}
