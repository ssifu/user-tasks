package com.samiulsifat.task_management.repository;

import com.samiulsifat.task_management.model.Task;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository {
    String addTask(Task task, String jwtToken);
    String removeTask(String taskId);
    String assignTask(String taskId, String username);
    List<Task> getAllTask();
}
