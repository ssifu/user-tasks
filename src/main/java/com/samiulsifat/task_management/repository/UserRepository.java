package com.samiulsifat.task_management.repository;

import com.samiulsifat.task_management.response.ApiResponse;
import com.samiulsifat.task_management.model.Task;
import com.samiulsifat.task_management.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository {
    void addUser(User user);
    User findByUsername(String username);
    List<User> findAllUser();
    List<Task> findAllTasksByUsername(String username);
    ApiResponse updateUserToAdmin(String userId);
}
