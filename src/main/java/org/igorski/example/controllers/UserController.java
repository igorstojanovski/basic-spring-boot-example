package org.igorski.example.controllers;

import org.igorski.example.model.User;
import org.igorski.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping
    public Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "{id}")
    public Iterable<User> getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }
}
