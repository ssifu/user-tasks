package com.samiulsifat.task_management.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.samiulsifat.task_management.response.ApiResponse;
import com.samiulsifat.task_management.model.Task;
import com.samiulsifat.task_management.model.User;
import com.samiulsifat.task_management.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.samiulsifat.task_management.model.Role.ADMIN;

@Service
public class UserService implements UserRepository {

    private final DynamoDBMapper dynamoDBMapper;

    public UserService(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    @Override
    public void addUser(User user) {
        dynamoDBMapper.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return dynamoDBMapper.load(User.class, username);
    }

    @Override
    public List<User> findAllUser() {
        return dynamoDBMapper.scan(User.class, new DynamoDBScanExpression());
    }

    @Override
    public List<Task> findAllTasksByUsername(String username) {
        Task task = new Task();
        task.setAssignedTo(username);

        DynamoDBQueryExpression<Task> queryExpression =
                new DynamoDBQueryExpression<Task>()
                        .withHashKeyValues(task)
                        .withIndexName("AssignedToIndex")
                        .withConsistentRead(false);

        return dynamoDBMapper.query(Task.class, queryExpression);
    }

    @Override
    public ApiResponse updateUserToAdmin(String userId) {
        User user = dynamoDBMapper.load(User.class, userId);
        if (user == null) {
            return new ApiResponse("Error", "User not found", null);
        }
        user.getRoles().add(ADMIN);
        return new ApiResponse("Success", "User '" + user.getUsername() + "' is now an admin", user);
    }


}
/*
aws dynamodb create-table --table-name Users --attribute-definitions AttributeName=username,AttributeType=S --key-schema AttributeName=username,KeyType=HASH --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5

* */