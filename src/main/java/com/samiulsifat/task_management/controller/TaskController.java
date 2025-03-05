package com.samiulsifat.task_management.controller;

import com.samiulsifat.task_management.model.Task;
import com.samiulsifat.task_management.service.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllTasks() {
        return ResponseEntity.status(HttpStatus.FOUND).body(new ApiResponse("Success", "List of all the tasks",taskService.getAllTask()));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTask(HttpServletRequest request, @RequestBody Task task) {
        String authHeader = request.getHeader("Authorization");
        task.setStatus("Pending");
        return taskService.createTask(task, authHeader.substring(7));
    }

    @PostMapping("/assign")
    public ResponseEntity<?> assignTask(@RequestParam("task_id") String taskId, @RequestParam("username") String username) {
        return taskService.assignTask(taskId, username);
    }

    @PutMapping("/update/status")
    public ResponseEntity<?> updateTaskStatus(@RequestParam("task_id") String taskId, @RequestParam("status") String status) {
        return taskService.updateTaskStatus(taskId, status);
    }

}
