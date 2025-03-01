package com.samiulsifat.task_management.service;

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

    public User signup(RegisterDto input) {

        User existingUser = userService.findByUsername(input.getUsername());

        System.out.println("Existing User: " + existingUser);

        if (existingUser != null) {
            throw new RuntimeException("Username is already exists");
        }

        User user = new User(
                input.getUsername(),
                passwordEncoder.encode(input.getPassword()),
                input.getRole()
        );
        userService.addUser(user);
        return user;
    }

    public User authenticate(LoginDto input) {
        System.out.println("Inside authenticate");
        System.out.println("Username: " + input.getUsername());
        System.out.println("Password: " + passwordEncoder.encode(input.getPassword()));
        System.out.println("Password match: " + passwordEncoder.matches(input.getPassword(), userService.findByUsername(input.getUsername()).getPassword()));
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(input.getUsername(), passwordEncoder.encode(input.getPassword()))
        );
        System.out.println("isAuthenticated: " + authentication.isAuthenticated());
        if (authentication.isAuthenticated()) {
            System.out.println("The user successfully authenticated");
            return userService.findByUsername(input.getUsername());
        } else {
            System.out.println("The user not found");
        }
        return null;
    }

}
