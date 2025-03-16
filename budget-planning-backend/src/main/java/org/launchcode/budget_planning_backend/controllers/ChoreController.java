package org.launchcode.budget_planning_backend.controllers;

import org.launchcode.budget_planning_backend.models.Chore;
import org.launchcode.budget_planning_backend.models.ChoreDto;
import org.springframework.web.bind.annotation.*;

import static org.launchcode.budget_planning_backend.models.Chore.createChore;

@RestController
@RequestMapping(value = "/chores")
@CrossOrigin(origins = "http://localhost:5173")

public class ChoreController {

    @PostMapping(value="/create")
    public Chore postChore(@RequestBody ChoreDto choreDto) {
        return createChore(choreDto.getName(), choreDto.getDescription(), choreDto.getAmountOfEarnings());
    }
}
