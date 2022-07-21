package com.example.onlinemarket.service;

import com.example.onlinemarket.entity.User;
import org.springframework.http.ResponseEntity;

/**
 * This interface hold methods for creating,getting and manipulating Users
 */
public interface UserService {

    void initializeUsers();

    ResponseEntity<User> createUser(User user);

    ResponseEntity<User> updateUserData(long id, User user);

    ResponseEntity<User> getUserById(long id);
}
