package org.launchcode.budget_planning_backend.controllers;

import jakarta.validation.Valid;
import org.launchcode.budget_planning_backend.models.Chore;
import org.launchcode.budget_planning_backend.models.ChoreDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.launchcode.budget_planning_backend.models.Chore.createNewChore;

@RestController
@RequestMapping(value = "/chores")
@CrossOrigin(origins = "http://localhost:5173")
public class ChoreController {

    private final Logger logger = LoggerFactory.getLogger(ChoreController.class);

    List<Chore> allChores = new ArrayList<>();
    List<Chore> choresByGroup = new ArrayList<>();

    @PostMapping(value = "/create")
    public void postChore(@Valid @RequestBody ChoreDto choreDto) {
        Chore chore = createNewChore(choreDto);
        allChores.add(chore);
        logger.info("New Chore created: ".concat(chore.toString()));
    }

    @GetMapping("/{userGroupId}/list")
    public ResponseEntity<List<Chore>> listAllChoresForGivenUserGroup(@PathVariable int userGroupId) {
        choresByGroup.clear();
        for (Chore chore : allChores) {
            if (chore.getGroup().getId() == userGroupId) {
                choresByGroup.add(chore);
            }
        }
        if (choresByGroup.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
        return ResponseEntity.ok(choresByGroup);
    }

    @GetMapping("/{userGroupId}/{id}")
    public ResponseEntity<Chore> listChoreDetails(@PathVariable int userGroupId, @PathVariable Integer id) {
        for (Chore chore : allChores) {
            if (chore.getGroup().getId() == userGroupId && chore.getId() == id) {
                return ResponseEntity.ok(chore);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
