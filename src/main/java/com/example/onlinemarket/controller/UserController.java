package com.example.onlinemarket.controller;

import com.example.onlinemarket.entity.User;
import com.example.onlinemarket.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This class is a controller for users, it holds methods that are requests for adding, getting and updating users
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * This method add user to the database
     *
     * @param user User to be added to the database
     * @return the added user
     */
    @PostMapping(path = "/add", produces = "application/json")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    /**
     * This method updates the user data
     *
     * @param id   of the user we want to update
     * @param user the values to update
     * @return the updated user
     */
    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUserData(@PathVariable long id, @RequestBody User user) {
        return userService.updateUserData(id, user);
    }

    /**
     * This method gives us the user by the given id
     *
     * @param id - long the id of the user we want to get
     * @return the user that have the given id or http status not found if there isn't user in the database with this id
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id) {
        return userService.getUserById(id);
    }
}
