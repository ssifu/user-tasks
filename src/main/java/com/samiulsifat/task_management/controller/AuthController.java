package com.samiulsifat.task_management.controller;

import com.samiulsifat.task_management.dto.LoginDto;
import com.samiulsifat.task_management.dto.RegisterDto;
import com.samiulsifat.task_management.model.User;
import com.samiulsifat.task_management.service.AuthenticationService;
import com.samiulsifat.task_management.service.JwtService;
import com.samiulsifat.task_management.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDto user) {
        if (user.getRoles() == null) {
            user.setRoles(Set.of(USER));
        } else {
            user.getRoles().add(USER);
        }

        ApiResponse response = authenticationService.signup(user);

        if ("Error".equals(response.getStatus())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LoginDto loginDto) {
        User authenticatedUser = authenticationService.authenticate(loginDto);
        if (authenticatedUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("Error", "Username or password wrong", loginDto));
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse("Success", "Login successful", jwtService.generateToken(authenticatedUser.getUsername())));
    }
}
