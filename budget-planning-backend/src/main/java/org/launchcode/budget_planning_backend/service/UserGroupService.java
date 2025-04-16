package org.launchcode.budget_planning_backend.service;

import jakarta.servlet.http.HttpServletRequest;
import org.launchcode.budget_planning_backend.controllers.AuthenticationController;
import org.launchcode.budget_planning_backend.data.UserGroupRepository;
import org.launchcode.budget_planning_backend.models.Chore;
import org.launchcode.budget_planning_backend.models.Event;
import org.launchcode.budget_planning_backend.models.User;
import org.launchcode.budget_planning_backend.models.UserGroup;
import org.launchcode.budget_planning_backend.models.dto.UserGroupDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class contains all the service methods/functions used to create and manage UserGroups.
 */
@Service
public class UserGroupService {

    @Autowired
    UserGroupRepository userGroupRepository;

    @Autowired
    AuthenticationController authenticationController;


    private final Logger logger = LoggerFactory.getLogger(UserGroupService.class);

    public void saveGroups(UserGroup group) {
        userGroupRepository.save(group);
    }

    public UserGroup getGroupByID(int id) {
        Optional<UserGroup> group = userGroupRepository.findById(id);
        return group.orElse(null);
    }

    public UserGroup getGroupByName(String groupName) {
        for (UserGroup group : userGroupRepository.findAll()) {
            if (group.getName().equals(groupName)) {
                return group;
            }
        }
        return null;
    }

    public List<UserGroup> getGroupsByUser(int userID) {
        List<UserGroup> groupsByUser = new ArrayList<>();
        for (UserGroup group : userGroupRepository.findAll()) {
            for (User user : group.getUsers()) {
                if (user.getId() == userID && !groupsByUser.contains(group)) {
                    groupsByUser.add(group);
                }
            }
        }
        return groupsByUser;
    }

    public UserGroup createNewGroup(UserGroupDTO groupDTO, HttpServletRequest request) {
        UserGroup group = new UserGroup(groupDTO.getName(), groupDTO.getDescription());
        User currentUser = authenticationController.getUserFromSession(request.getSession());
        if (currentUser != null) {
            group.addUsers(currentUser);
        }
        logger.info("New Group: ".concat(group.toString()));
        return group;
    }

    public List<Chore> getChoresFromGroup(int groupID) {
        UserGroup group = getGroupByID(groupID);
        if(group != null){
            return group.getChores();
        }
        return null;
    }

    public List<Event> getEventsFromGroup(int groupID) {
        UserGroup group = getGroupByID(groupID);
        if(group != null){
            return group.getEvents();
        }
        return null;
    }

    public List<User> getUsersFromGroup(int groupID) {
        UserGroup group = getGroupByID(groupID);
        if(group != null){
            return group.getUsers();
        }
        return null;
    }

    public void addUsersToGroup(int groupID, User user) {
        UserGroup group = getGroupByID(groupID);
        group.addUsers(user);
        userGroupRepository.save(group);
    }

    public void editGroupByID(int groupID, UserGroupDTO userGroupDTO) {
        UserGroup group = getGroupByID(groupID);
        group.setName(userGroupDTO.getName());
        group.setDescription(userGroupDTO.getDescription());
        userGroupRepository.save(group);
    }

    public void deleteGroupByID(Integer groupID) {
        if(groupID != null) {
            removeAllUsersFromGroup(groupID);
            logger.info("Deleted group with ID={}", groupID);
            userGroupRepository.deleteById(groupID);
        } else {
            logger.info("Group with ID={} does not exist. Unable to delete group.", groupID);
        }
    }

    public boolean hasAccessToGroups(int groupID, int userID) {
        UserGroup group = getGroupByID(groupID);
        if (group == null) {
            return false;
        }
        for (User user : getUsersFromGroup(groupID)) {
            if (user.getId() == userID) {
                return true;
            }
        }
        return false;
    }

    public void removeAllUsersFromGroup(Integer groupID) {
        UserGroup group = getGroupByID(groupID);
        group.removeUsers();
        logger.info("Removed users from "+group.getName());
        userGroupRepository.save(group);
    }
}
