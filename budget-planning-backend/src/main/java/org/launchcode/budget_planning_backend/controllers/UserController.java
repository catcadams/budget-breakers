package org.launchcode.budget_planning_backend.controllers;
import org.launchcode.budget_planning_backend.models.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

private final List<User> users = new ArrayList<>();

    @GetMapping
    public List<User> getUsers() {
        return users;
    }

    @PostMapping
    public List<User> addUser(@RequestBody User newUser) {
        users.add(newUser);
        return users;
    }
}
