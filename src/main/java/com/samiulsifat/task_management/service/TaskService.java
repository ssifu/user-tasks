package com.samiulsifat.task_management.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.samiulsifat.task_management.model.Task;
import com.samiulsifat.task_management.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class TaskService implements TaskRepository {

    private final JwtService jwtService;
    private final DynamoDBMapper dynamoDBMapper;

    public TaskService(JwtService jwtService, DynamoDBMapper dynamoDBMapper) {
        this.jwtService = jwtService;
        this.dynamoDBMapper = dynamoDBMapper;
    }

    @Override
    public String addTask(Task task, String jwtToken) {

        Task existingTask = dynamoDBMapper.load(Task.class, task.getTaskId());

        if (existingTask != null) {
            return "Task id already present";
        }
        task.setAssignedBy(jwtService.extractUsername(jwtToken));
        task.setCreatedAt(String.valueOf(new SimpleDateFormat("MM/dd/yyyy").format(new Date())));
        dynamoDBMapper.save(task);
        return "Task created successfully" + task.toString();
    }

    @Override
    public String removeTask(String taskId) {
        return "";
    }

    @Override
    public String assignTask(String taskId, String username) {
        Task task = dynamoDBMapper.load(Task.class, taskId);
        if (task == null) {
            return "Task with the provided task id is not available";
        }
        task.setAssignedTo(username);
        dynamoDBMapper.save(task);
        return "Task assigned to " + username + ", Task: " + dynamoDBMapper.load(Task.class, taskId);
    }

    @Override
    public List<Task> getAllTask() {
        return dynamoDBMapper.scan(Task.class, new DynamoDBScanExpression());
    }

    @Override
    public String updateTaskStatus(String taskId, String status) {
        Task task = dynamoDBMapper.load(Task.class, taskId);
        if (task == null) {
            return "Task with taskId " + taskId + " is not available";
        }
        task.setStatus(status);
        dynamoDBMapper.save(task);
        return "Task with id " + taskId + " status updated to" + status;
    }
}
