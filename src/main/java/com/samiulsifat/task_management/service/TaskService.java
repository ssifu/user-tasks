package com.samiulsifat.task_management.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.samiulsifat.task_management.response.ApiResponse;
import com.samiulsifat.task_management.model.Role;
import com.samiulsifat.task_management.model.Task;
import com.samiulsifat.task_management.model.User;
import com.samiulsifat.task_management.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class TaskService implements TaskRepository {

    private final JwtService jwtService;
    private final DynamoDBMapper dynamoDBMapper;
    private final UserService userService;

    public TaskService(JwtService jwtService, DynamoDBMapper dynamoDBMapper, UserService userService) {
        this.jwtService = jwtService;
        this.dynamoDBMapper = dynamoDBMapper;
        this.userService = userService;
    }

    @Override
    public ResponseEntity<?> createTask(Task task, String jwtToken) {

        // Check if the task with the given task id already exists
        Task existingTask = dynamoDBMapper.load(Task.class, task.getTaskId());

        if (existingTask != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse("Error", "Task with task id " + task.getTaskId() + " is already present", null)
            );
        }

        // Extract username from the provided JWT token to check if the user ADMIN or not
        String username = jwtService.extractUsername(jwtToken);

        // Get the user to check if the user ADMIN or not
        User user = userService.findByUsername(username);

        // If the user is not an ADMIN then check if the user is trying to assign a task to another user
        // As users can only assign tasks to themselves, if found the user is assigning task to another user
        // return BAD REQUEST response, otherwise, set the assignedTo to the user. Also, if ADMIN is not providing
        // the assignedTo field then assign the task to the ADMIN.
        if (!user.getRoles().contains(Role.ADMIN)) {
            if (task.getAssignedTo() != null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ApiResponse("Error", "User can only assign task to self", null)
                );
            task.setAssignedTo(username);
        } else {
            if (task.getAssignedTo() == null) {
                task.setAssignedTo(username);
            }
        }

        // Set default values like task assignee and task creation time
        task.setAssignedBy(username);
        task.setCreatedAt(new SimpleDateFormat("MM/dd/yyyy").format(new Date()));

        // Insert task data to database
        dynamoDBMapper.save(task);

        // Return 200 ok response
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse("Success", "Task created successfully", task)
        );
    }

    @Override
    public String removeTask(String taskId) {
        return "";
    }

    @Override
    public ResponseEntity<?> assignTask(String taskId, String username) {
        Task task = dynamoDBMapper.load(Task.class, taskId);
        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse("Error", "Task with the provided task id is not available", null)
            );
        }
        task.setAssignedTo(username);
        dynamoDBMapper.save(task);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse("Success", "Task assigned to " + username, dynamoDBMapper.load(Task.class, taskId))
        );
    }

    @Override
    public List<Task> getAllTask() {
        return dynamoDBMapper.scan(Task.class, new DynamoDBScanExpression());
    }

    @Override
    public ResponseEntity<?> updateTaskStatus(String taskId, String status) {
        Task task = dynamoDBMapper.load(Task.class, taskId);
        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse(
                            "Error",
                            "Task with the provided task id is not available",
                            null
                    )
            );
        }
        task.setStatus(status);
        dynamoDBMapper.save(task);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(
                        "Success",
                        "Task with id " + taskId + " status updated to" + status,
                        dynamoDBMapper.load(Task.class, taskId)
                )
        );
    }
}
