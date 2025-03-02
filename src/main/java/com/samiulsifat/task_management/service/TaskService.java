package com.samiulsifat.task_management.service;

import com.samiulsifat.task_management.model.Task;
import com.samiulsifat.task_management.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService implements TaskRepository {

    @Override
    public String addTask(Task task) {
        return "";
    }

    @Override
    public String removeTask(String taskId) {
        return "";
    }

    @Override
    public String assignTask(String taskId, String username) {
        return "";
    }

    @Override
    public List<Task> getAllTask() {
        return List.of();
    }
}
