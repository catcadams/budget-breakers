package org.launchcode.budget_planning_backend.controllers;

import org.launchcode.budget_planning_backend.models.UserGroup;
import org.launchcode.budget_planning_backend.models.dto.UserGroupDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="/groups")
@CrossOrigin
public class UserGroupController {

    private final Logger logger = LoggerFactory.getLogger(UserGroupController.class);

    private final List<UserGroup> groupsList = new ArrayList<>();

    private final List<UserGroup> groupsByUser = new ArrayList<>();

    @PostMapping(value="/create")
    public void postGroups(@RequestBody UserGroupDTO userGroupDTO) {
        UserGroup group = new UserGroup(userGroupDTO.getName(), userGroupDTO.getDescription());
        groupsList.add(group);
        logger.info("New Group: ".concat(group.toString()));
    }

}
