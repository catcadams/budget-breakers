package org.launchcode.budget_planning_backend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.launchcode.budget_planning_backend.models.User;
import org.launchcode.budget_planning_backend.models.UserGroup;
import org.launchcode.budget_planning_backend.models.dto.UserGroupDTO;
import org.launchcode.budget_planning_backend.service.EmailService;
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
    EmailService emailService;

    @Autowired
    AuthenticationController authenticationController;

    /**
     * Creates a new group based on the user input. Sends email to a user if email is provided for additional members.
     * @param userGroupDTO the DTO containing data required to create a new group.
     * @param request used to retrieve the user in session and set that user as the first group member.
     * @return a {@link ResponseEntity} message to acknowledge successful group creation.
     */
    @PostMapping(value="/create")
    public ResponseEntity<String> createNewGroup(@RequestBody UserGroupDTO userGroupDTO, HttpServletRequest request) {

        UserGroup group = groupService.createNewGroup(userGroupDTO, request);
        groupService.saveGroups(group);
        String groupName = userGroupDTO.getName();
        String groupDescription = userGroupDTO.getDescription();
        List<String> emails = userGroupDTO.getEmails();

        System.out.println("Emails received: " + emails);

        String subject = "You are invited to join " + groupName + " on Red, Green, VACAY!";

        for (String email : emails) {
            emailService.sendEmailInvites(email, subject, groupName, groupDescription, group);
        }
        return ResponseEntity.ok("Group created successfully!");
    }

    /**
     * Retrieves the list of groups in which the logged-in user is a member.
     * @param userID - the ID of the logged-in user.
     * @param groupID - the ID used to check if the user has access to the listed groups.
     * @param request - the request is used to retrieve the current session and the current user in the session.
     * @return a {@link ResponseEntity} containing a list of {@link UserGroup} objects;
     *          an empty list is returned if no groups exist for the user, or the user does not have access to the groups.
     */
    @GetMapping(value = "/{userID}/list")
    public ResponseEntity<Iterable<UserGroup>> displayGroupsBySpecifiedUser(@PathVariable Integer userID, Integer groupID, HttpServletRequest request) {
        User currentUser = authenticationController.getUserFromSession(request.getSession());
        if (groupID != null && !groupService.hasAccessToGroups(groupID, currentUser.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Collections.emptyList());
        }

        Iterable<UserGroup> groupsByUser = groupService.getGroupsByUser(userID);
        if(groupsByUser == null) {
            return ResponseEntity.status(HttpStatus.OK).body(Collections.emptyList());
        }
        return ResponseEntity.ok(groupsByUser);
    }

    /**
     * Retrieves the details of a single group by its ID.
     * @param groupID - the ID of the group to retrieve
     * @param request - the request that retrieves the current session and current user in session.
     * @return a {@link UserGroup} if group exists; if group does not exist, 404 Not Found is returned.
     */
    @GetMapping(value = "/{userID}/{groupID}")
    public ResponseEntity<UserGroup> displayGroupBySpecifiedID(@PathVariable Integer groupID, HttpServletRequest request) {
        User currentUser = authenticationController.getUserFromSession(request.getSession());
        if (!groupService.hasAccessToGroups(groupID, currentUser.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);  // If no access, return forbidden
        }

        UserGroup group = groupService.getGroupByID(groupID);
        if(group == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(group);
    }

    /**
     * Adds new member to group through email information and sends email invitation to added member.
     * @param groupID - the ID of the group to which the new member is being added.
     * @param userGroupDTO - the DTO that provides the email(s) for the added members
     * @return a {@link ResponseEntity} containing an "Invitation send successfully" message after email(s) is sent.
     *          If no emails are provided or emails are null, an error message is returned instead.
     */
    @PostMapping(value = "/{userID}/{groupID}/add-member")
    public ResponseEntity<String> addMembersToGroup( @PathVariable Integer groupID, @RequestBody UserGroupDTO userGroupDTO) {
        UserGroup group = groupService.getGroupByID(groupID);
        String groupName = group.getName();
        String groupDescription = group.getDescription();
        List<String> emails = userGroupDTO.getEmails();

        if (emails == null || emails.isEmpty()) {
            return ResponseEntity.badRequest().body("No emails provided.");
        }

        String subject = "You are invited to join " + groupName + " on Red, Green, VACAY!";

        for (String email : emails) {
            emailService.sendEmailInvites(email, subject, groupName, groupDescription, group);
        }
        return ResponseEntity.ok("Member invitation sent successfully!");

    }

    /**
     * Updates the group with the specified ID
     * @param groupID - the ID of the group to update.
     * @param userGroupDTO - contains new Group field values from user inputs.
     */
    @PutMapping(value = "/{userID}/{groupID}/edit")
    public void editGroupDetailsByID(@PathVariable Integer groupID, @RequestBody UserGroupDTO userGroupDTO) {
        groupService.editGroupByID(groupID, userGroupDTO);
    }

    /**
     * Deletes the group with the specified ID
     * @param groupID - the ID of the group to delete
     */
    @DeleteMapping(value = "/{userID}/{groupID}/delete")
    public void deleteGroupByID(@PathVariable Integer groupID) {
        groupService.deleteGroupByID(groupID);
    }
}
