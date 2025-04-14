package org.launchcode.budget_planning_backend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.launchcode.budget_planning_backend.models.Chore;
import org.launchcode.budget_planning_backend.models.ChoreDto;
import org.launchcode.budget_planning_backend.service.ChoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/chores")
@CrossOrigin
public class ChoreController {

    @Autowired
    ChoreService choreService;

    private final Logger logger = LoggerFactory.getLogger(ChoreController.class);

    /**
     * Creates a new chore based on the provided data as user inputs.
     * @param choreDto - the DTO containing data required to create a new chore
     */
    @PostMapping(value = "/create")
    public void postChore(@Valid @RequestBody ChoreDto choreDto) {
        choreService.saveChore(choreService.createNewChore(choreDto));
    }

    /**
     * Retrieves all chores associated with a specific user group.
     * @param userGroupId - the ID of the user group whose chores are to be retrieved
     * @return a {@link ResponseEntity} containing a list of {@link Chore} objects;
     *         an empty list is returned if no chores exist for the specified group
     */
    @GetMapping("/{userGroupId}/list")
    public ResponseEntity<List<Chore>> listAllChoresForGivenUserGroup(@PathVariable int userGroupId) {
        List<Chore> choresByGroup = choreService.getChoresByGroup(userGroupId);
        if (choresByGroup.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(Collections.emptyList());
        }
        return ResponseEntity.ok(choresByGroup);
    }

    /**
     * Retrieves the details of a specific chore by its ID.
     * @param id - the ID of the chore to retrieve
     * @return {@link Chore} if chore exists otherwise return 404 Not Found if the chores does not exist
     */
    @GetMapping("/{userGroupId}/{id}")
    public ResponseEntity<Chore> listChoreDetails(@PathVariable Integer id) {
        Chore chore = choreService.getChoreById(id);
        if (chore != null) {
            return ResponseEntity.ok(chore);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    /**
     * Updates the chore with the specified ID
     * @param id - the ID of the chore to update
     * @param choreDto - contains new Chore field values from user inputs
     */
    @PutMapping("/{id}/edit")
    public void editChoreById(@PathVariable Integer id, @Valid @RequestBody ChoreDto choreDto) {
        choreService.updateChoreDetailsByChoreId(id, choreDto);
    }

    /**
     * Deletes the chore with the specified ID
     * @param id - the ID of the chore to delete
     */
    @DeleteMapping("/delete/{id}")
    public void deleteChoreById(@PathVariable Integer id) {
        choreService.deleteChoreById(id);
    }

    /**
     * Assigns the chore with the specified ID to the user in session.
     * @param id - the ID of the chore to assign to the current user
     * @return a {@link ResponseEntity} containing the updated {@link Chore} if successful,
     *        or a 404 Not Found response if the chore does not exist
     */
    @PutMapping("/{id}/assign")
    public ResponseEntity<Chore> assignChore(@PathVariable Integer id, HttpServletRequest request) {
        Chore chore = choreService.assignChoreToTheUser(id, request);
        if (chore != null) {
            return ResponseEntity.ok(chore);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    /**
     * Unassigns the chore with the specified ID from its current assignee.
     * @param id - the ID of the chore to unassign from the current user
     * @return a {@link ResponseEntity} containing the updated {@link Chore} if successful,
     *       or a 404 Not Found response if the chore does not exist
     */
    @PutMapping("/{id}/unassign")
    public ResponseEntity<Chore> unassignChore(@PathVariable Integer id, HttpServletRequest request) {
        Chore chore = choreService.unassignChore(id, request);
        if (chore != null) {
            return ResponseEntity.ok(chore);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    /**
     * Marks the chore with the given ID as completed by a minor.
     * @param id - the ID of the chore to mark as completed
     * @return  {@link ResponseEntity} containing the updated {@link Chore} if found,
     *          or a 404 Not Found response if the chore does not exist
     */
    @PutMapping("/{id}/complete")
    public ResponseEntity<Chore> completeChore(@PathVariable Integer id, @RequestBody Map<String, Integer> payload,
                                               HttpServletRequest request) {
        Chore chore = choreService.completeChoreByMinor(id, payload.get("eventId"), payload.get("groupId"), request);
        if (chore != null) {
            return ResponseEntity.ok(chore);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    /**
     * Confirms the completion of a chore by an adult.
     * @param id - the ID of the chore to confirm
     * @return  {@link ResponseEntity} containing the updated {@link Chore} if found,
     *        or a 404 Not Found response if the chore does not exist
     */
    @PutMapping("/{id}/contribute")
    public ResponseEntity<Chore> confirmChore(@PathVariable Integer id) {
        Chore chore = choreService.confirmChoreByAdult(id);
        if (chore != null) {
            return ResponseEntity.ok(chore);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    /**
     * Rejects the completion of a chore by an adult.Rejected chore becomes Open again.
     * @param id - the ID of the chore to reject
     * @return  {@link ResponseEntity} containing the updated {@link Chore} if found,
     *        or a 404 Not Found response if the chore does not exist
     */
    @PutMapping("/{id}/reject")
    public ResponseEntity<Chore> rejectChore(@PathVariable Integer id) {
        Chore chore = choreService.rejectChoreByAdult(id);
        if (chore != null) {
            return ResponseEntity.ok(chore);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
