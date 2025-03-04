package com.samiulsifat.task_management.controller;

import com.samiulsifat.task_management.model.Task;
import com.samiulsifat.task_management.service.TaskService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
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
    public List<Task> getAllTasks() {
        return taskService.getAllTask();
    }

    @PostMapping
    public String createTask(HttpServletRequest request, @RequestBody Task task) {
        String authHeader = request.getHeader("Authorization");
        task.setStatus("Pending");
        return taskService.addTask(task, authHeader.substring(7));
    }

    @PostMapping("/assign")
    public String assignTask(@RequestParam("task_id") String taskId, @RequestParam("username") String username) {
        return taskService.assignTask(taskId, username);
    }

    @PutMapping("/update/status")
    public String updateTaskStatus(@RequestParam("task_id") String taskId, @RequestParam("status") String status) {
        return taskService.updateTaskStatus(taskId, status);
    }

}
