package org.launchcode.budget_planning_backend.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Email;
import org.launchcode.budget_planning_backend.controllers.AuthenticationController;
import org.launchcode.budget_planning_backend.data.UserGroupRepository;
import org.launchcode.budget_planning_backend.data.UserRepository;
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

@Service
public class UserGroupService {

    @Autowired
    UserGroupRepository userGroupRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    AuthenticationController authenticationController;


    private final Logger logger = LoggerFactory.getLogger(UserGroupService.class);

//    public final List<UserGroup> groupsList = new ArrayList<>();
//    private final List<UserGroup> groupsByUser = new ArrayList<>();

    public void saveGroups(UserGroup group) {
        userGroupRepository.save(group);
    }


    public UserGroup getGroupByID(int id) {
        Optional<UserGroup> group = userGroupRepository.findById(id);
        if (group.isEmpty()) {
            return null;
        }
        return group.get();

//        for (UserGroup group : groupsList) {
//            if(group.getId() == id) {
//                return group;
//            }
//        }
//        return null;
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
//        Optional<User> user = userRepository.findById(userID);
//        if (user.isEmpty()) {
//            return null;
//        } return user.get().getUserGroups();

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
//        User currentUser = authenticationService.getCurrentUser(request);
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
//        for (UserGroup group : groupsList) {
//            if (group.getId() == groupID) {
//                return group.getChores();
//            }
//        }
        return null;
    }

    public List<Event> getEventsFromGroup(int groupID) {
        UserGroup group = getGroupByID(groupID);
        if(group != null){
            return group.getEvents();
        }
//        for (UserGroup group : groupsList) {
//            if (group.getId() == groupID) {
//                return group.getEvents();
//            }
//        }
        return null;
    }

    public List<User> getUsersFromGroup(int groupID) {
        UserGroup group = getGroupByID(groupID);
        if(group != null){
            return group.getUsers();
        }
//        for (UserGroup group : groupsList) {
//            if (group.getId() == groupID) {
//                return group.getUsers();
//            }
//        }
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
    }

    public void deleteGroupByID(Integer groupID) {
        if(groupID != null) {
            logger.info("Deleted group with ID={}", groupID);
            userGroupRepository.deleteById(groupID);
        } else {
            logger.info("Group with ID={} does not exist. Unable to delete group.", groupID);
        }

//        UserGroup groupToDelete = null;
//        for (UserGroup group : groupsList) {
//            if (group.getId() == groupID) {
//                groupToDelete = group;
//                logger.info("Deleted group with ID={}", groupID);
//            }
//        }
//        if (groupToDelete != null) {
//            groupsList.remove(groupToDelete);
//            groupsByUser.remove(groupToDelete);
//            logger.info("Deleted group with ID={}", groupID);
//        } else {
//            logger.info("Group with ID={} does not exist. Unable to delete group.", groupID);
//        }
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
}
