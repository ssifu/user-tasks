package com.samiulsifat.task_management.repository;

import com.samiulsifat.task_management.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository {
    void addUser(User user);
//    void removeUser(String username);
    User findByUsername(String username);
    List<User> findAllUser();
}
