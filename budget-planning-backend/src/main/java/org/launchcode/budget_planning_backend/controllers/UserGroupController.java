package org.launchcode.budget_planning_backend.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;
import org.launchcode.budget_planning_backend.models.DummyObjectsToBeDeleted;
import org.launchcode.budget_planning_backend.models.User;
import org.launchcode.budget_planning_backend.models.UserGroup;
import org.launchcode.budget_planning_backend.models.dto.UserGroupDTO;
import org.launchcode.budget_planning_backend.service.UserGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.launchcode.budget_planning_backend.models.DummyObjectsToBeDeleted.getUserByID;

@RestController
@RequestMapping(value="/groups")
@CrossOrigin
public class UserGroupController {

    private final Logger logger = LoggerFactory.getLogger(UserGroupController.class);

    @Autowired
    UserGroupService groupService;


    @PostMapping(value="/create")
    public void postGroups(@RequestBody UserGroupDTO userGroupDTO, HttpServletRequest request) {
        groupService.saveGroups(groupService.createNewGroup(userGroupDTO, request));
    }

    @GetMapping(value = "/{userID}/{groupID}")
    public ResponseEntity<List<UserGroup>> displayGroupsBySpecifiedUser(@PathVariable int userID) {
        List<UserGroup> groupsByUser = groupService.getGroupsByUser(userID);
        if(groupsByUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(Collections.emptyList());
        }
        return ResponseEntity.ok(groupsByUser);
    }

}
