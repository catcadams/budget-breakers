package org.launchcode.budget_planning_backend.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;
import org.launchcode.budget_planning_backend.models.DummyObjectsToBeDeleted;
import org.launchcode.budget_planning_backend.models.User;
import org.launchcode.budget_planning_backend.models.UserGroup;
import org.launchcode.budget_planning_backend.models.dto.UserGroupDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.launchcode.budget_planning_backend.models.DummyObjectsToBeDeleted.getUserByID;

@RestController
@RequestMapping(value="/groups")
@CrossOrigin
public class UserGroupController {

    private final Logger logger = LoggerFactory.getLogger(UserGroupController.class);

    public final List<UserGroup> groupsList = new ArrayList<>();

    private final List<UserGroup> groupsByUser = new ArrayList<>();

    private final User user = getUserByID(1);

    @PostMapping(value="/create")
    public void postGroups(@RequestBody UserGroupDTO userGroupDTO) {
        UserGroup group = new UserGroup(userGroupDTO.getName(), userGroupDTO.getDescription());
        groupsList.add(group);
        if(user != null && user.getId() == 1) {
            group.addUsers(user);
        }
            logger.info("New Group: ".concat(group.toString()));
    }

}
