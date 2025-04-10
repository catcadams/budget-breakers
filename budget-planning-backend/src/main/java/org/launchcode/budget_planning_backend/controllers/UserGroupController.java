package org.launchcode.budget_planning_backend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Email;
import org.launchcode.budget_planning_backend.models.User;
import org.launchcode.budget_planning_backend.models.UserGroup;
import org.launchcode.budget_planning_backend.models.dto.UserGroupDTO;
import org.launchcode.budget_planning_backend.service.AuthenticationService;
import org.launchcode.budget_planning_backend.service.UserGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping(value="/groups")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class UserGroupController {

    private final Logger logger = LoggerFactory.getLogger(UserGroupController.class);

    @Autowired
    UserGroupService groupService;

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping(value="/create")
    public void createNewGroup(@RequestBody UserGroupDTO userGroupDTO, HttpServletRequest request) {
        User currentUser = authenticationService.getCurrentUser(request);
        groupService.saveGroups(groupService.createNewGroup(userGroupDTO, request));
    }

    @GetMapping(value = "/{userID}/list")
    public ResponseEntity<List<UserGroup>> displayGroupsBySpecifiedUser(@PathVariable Integer userID, Integer groupID, HttpServletRequest request) {
        User currentUser = authenticationService.getCurrentUser(request);  // Get the authenticated user
        if (groupID != null && !groupService.hasAccessToGroups(groupID, currentUser.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Collections.emptyList());
        }

        List<UserGroup> groupsByUser = groupService.getGroupsByUser(userID);
        if(groupsByUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(Collections.emptyList());
        }
        return ResponseEntity.ok(groupsByUser);
    }

    @GetMapping(value = "/{userID}/{groupID}")
    public ResponseEntity<UserGroup> displayGroupBySpecifiedID(@PathVariable Integer groupID, HttpServletRequest request) {
        User currentUser = authenticationService.getCurrentUser(request);
        if (!groupService.hasAccessToGroups(groupID, currentUser.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);  // If no access, return forbidden
        }

        UserGroup group = groupService.getGroupByID(groupID);
        if(group == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(group);
    }

    @PostMapping(value = "/{userID}/{groupID}/add-member")
    public void addMembersToGroup( @PathVariable Integer groupID, @RequestBody Email email) {
        groupService.addUsersToGroup(groupID, email);
    }


}
