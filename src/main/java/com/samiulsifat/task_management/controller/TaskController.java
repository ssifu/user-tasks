package com.samiulsifat.task_management.controller;

import com.samiulsifat.task_management.model.Task;
import com.samiulsifat.task_management.service.TaskService;
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
    public Task addTask(@RequestBody Task task) {
        taskService.addTask(task);
        return task;
    }

}
