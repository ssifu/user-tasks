package com.samiulsifat.task_management.controller;

import com.samiulsifat.task_management.dto.RegisterDto;
import com.samiulsifat.task_management.model.Task;
import com.samiulsifat.task_management.model.User;
import com.samiulsifat.task_management.response.ApiResponse;
import com.samiulsifat.task_management.service.AuthenticationService;
import com.samiulsifat.task_management.service.TaskService;
import com.samiulsifat.task_management.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.samiulsifat.task_management.model.Role.ADMIN;
import static com.samiulsifat.task_management.model.Role.USER;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final TaskService taskService;
    private final AuthenticationService authenticationService;

    public AdminController(UserService userService, TaskService taskService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.taskService = taskService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/users")
    public List<User> get() {
        return userService.findAllUser();
    }

    @GetMapping("/tasks")
    public ResponseEntity<?> getAllTasks() {
        List<Task> tasks = taskService.getAllTask();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse("Success", "All the tasks", tasks));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAdmin(@RequestBody RegisterDto user) {
        if (user.getRoles() == null || !user.getRoles().contains(USER)) {
            user.getRoles().add(USER);
        }
        user.getRoles().add(ADMIN);

        ApiResponse response = authenticationService.signup(user);

        if ("Error".equals(response.getStatus())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/update-user")
    public ResponseEntity<?> updateUserToAdmin(@RequestParam("user_id") String userId) {
        ApiResponse response = userService.updateUserToAdmin(userId);
        if (response.getStatus().equals("Error")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping
    public String delete() {
        return "DELETE :: Admin Controller";
    }

}
