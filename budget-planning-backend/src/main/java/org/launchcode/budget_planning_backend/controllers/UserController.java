package org.launchcode.budget_planning_backend.controllers;
import jakarta.servlet.http.HttpServletRequest;
import org.launchcode.budget_planning_backend.models.User;
import org.launchcode.budget_planning_backend.service.UserGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/userAccount")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class UserController {

    @Autowired
    AuthenticationController authenticationController;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

//private final List<User> users = new ArrayList<>();

    @GetMapping(value = "/{userID}")
    public ResponseEntity<User> displayUserAccountPage (HttpServletRequest request) {
        User currentUser = authenticationController.getUserFromSession(request.getSession());

        if (currentUser == null) {
            logger.info("User Not Found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        logger.info("User ID: " + currentUser.getId());
        return ResponseEntity.ok(currentUser);
    }

//    @PostMapping
//    public List<User> addUser(@RequestBody User newUser) {
//        users.add(newUser);
//        return users;
//    }
}
