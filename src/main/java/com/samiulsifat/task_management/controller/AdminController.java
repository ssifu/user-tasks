package com.samiulsifat.task_management.controller;

import com.samiulsifat.task_management.model.User;
import com.samiulsifat.task_management.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> get() {
        return userService.findAllUser();
    }

    @PostMapping
    public String post() {
        return "POST :: Admin Controller";
    }

    @PutMapping
    public String put() {
        return "PUT :: Admin Controller";
    }

    @DeleteMapping
    public String delete() {
        return "DELETE :: Admin Controller";
    }

}
