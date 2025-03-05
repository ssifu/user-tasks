package com.samiulsifat.task_management.controller;

import com.samiulsifat.task_management.model.Task;
import com.samiulsifat.task_management.model.User;
import com.samiulsifat.task_management.service.TaskService;
import com.samiulsifat.task_management.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final TaskService taskService;

    public AdminController(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
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
