package com.samiulsifat.task_management.service;

import com.samiulsifat.task_management.controller.ApiResponse;
import com.samiulsifat.task_management.dto.LoginDto;
import com.samiulsifat.task_management.dto.RegisterDto;
import com.samiulsifat.task_management.model.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserService userService,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager
    ) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public ApiResponse signup(RegisterDto input) {

        User existingUser = userService.findByUsername(input.getUsername());

        if (existingUser != null) {
            return new ApiResponse("Error", "User already exists", null);
        }

        User user = new User(
                input.getUsername(),
                passwordEncoder.encode(input.getPassword()),
                input.getRoles()
        );
        userService.addUser(user);
        return new ApiResponse("Success", "User registered successfully", user);
    }

    public User authenticate(LoginDto input) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(input.getUsername(), input.getPassword())
            );
            if (authentication.isAuthenticated()) {
                return userService.findByUsername(input.getUsername());
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

}
