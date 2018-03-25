package org.igorski.example.controller;

import org.igorski.example.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Igor Stojanovski.
 * Date: 3/14/2018
 * Time: 10:09 PM
 */
@RestController
@RequestMapping("/user")
public class AppUserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    public AppUser createUser(@RequestBody AppUser user) {
        return userService.createUser(user);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<AppUser> getAllUsers() {
        return userService.getAllUsers();
    }

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public Iterable<AppUser> getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }
}
