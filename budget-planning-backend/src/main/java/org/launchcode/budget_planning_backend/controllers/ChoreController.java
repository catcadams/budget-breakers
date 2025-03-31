package org.launchcode.budget_planning_backend.controllers;

import jakarta.validation.Valid;
import org.launchcode.budget_planning_backend.models.Chore;
import org.launchcode.budget_planning_backend.models.ChoreDto;
import org.launchcode.budget_planning_backend.models.Status;
import org.launchcode.budget_planning_backend.service.ChoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/chores")
@CrossOrigin(origins = "http://localhost:5173")
public class ChoreController {

    @Autowired
    ChoreService choreService;

    private final Logger logger = LoggerFactory.getLogger(ChoreController.class);


    @PostMapping(value = "/create")
    public void postChore(@Valid @RequestBody ChoreDto choreDto) {
        choreService.saveChore(choreService.createNewChore(choreDto));
    }

    @GetMapping("/{userGroupId}/list")
    public ResponseEntity<List<Chore>> listAllChoresForGivenUserGroup(@PathVariable int userGroupId) {
        List<Chore> choresByGroup = choreService.getChoresByGroup(userGroupId);
        if (choresByGroup.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(Collections.emptyList());
        }
        return ResponseEntity.ok(choresByGroup);
    }

    @GetMapping("/{userGroupId}/{id}")
    public ResponseEntity<Chore> listChoreDetails(@PathVariable Integer id) {
        Chore chore = choreService.getChoreById(id);
        if (chore != null) {
            return ResponseEntity.ok(chore);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PutMapping("/{id}/edit")
    public void editChoreById(@PathVariable Integer id, @Valid @RequestBody ChoreDto choreDto) {
        choreService.updateChoreDetailsByChoreId(id, choreDto);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @DeleteMapping("/delete/{id}")
    public void deleteChoreById(@PathVariable Integer id) {
        choreService.deleteChoreById(id);
    }

    @PutMapping("/{id}/assign")
    public ResponseEntity<Chore> assignChore(@PathVariable Integer id) {
        Chore chore = choreService.assignChoreToTheUser(id);
        if (chore != null) {
            return ResponseEntity.ok(chore);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<Chore> completeChore(@PathVariable Integer id) {
        Chore chore = choreService.completeChoreByMinor(id);
        if (chore != null) {
            return ResponseEntity.ok(chore);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PutMapping("/{id}/contribute")
    public ResponseEntity<Chore> confirmChore(@PathVariable Integer id) {
        Chore chore = choreService.confirmChoreByAdult(id);
        if (chore != null) {
            return ResponseEntity.ok(chore);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
