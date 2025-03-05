package com.samiulsifat.task_management.repository;

import com.samiulsifat.task_management.controller.ApiResponse;
import com.samiulsifat.task_management.model.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository {
    ResponseEntity<?> createTask(Task task, String jwtToken);
    String removeTask(String taskId);
    ResponseEntity<?> assignTask(String taskId, String username);
    List<Task> getAllTask();
    ResponseEntity<?> updateTaskStatus(String taskId, String status);
}
