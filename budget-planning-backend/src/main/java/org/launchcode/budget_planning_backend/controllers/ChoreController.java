package org.launchcode.budget_planning_backend.controllers;

import jakarta.validation.Valid;
import org.launchcode.budget_planning_backend.models.Chore;
import org.launchcode.budget_planning_backend.models.ChoreDto;
import org.launchcode.budget_planning_backend.models.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import static org.launchcode.budget_planning_backend.models.Chore.createChore;

@RestController
@RequestMapping(value = "/chores")
@CrossOrigin
public class ChoreController {

    private final Logger logger = LoggerFactory.getLogger(ChoreController.class);

    @PostMapping(value="/create")
    public Chore postChore(@Valid @RequestBody ChoreDto choreDto) {
        Chore chore = createChore(choreDto.getName(), choreDto.getDescription(), choreDto.getAmountOfEarnings());
        chore.setStatus(Status.OPEN);
        logger.info("New Chore created: ".concat(chore.toString()));
        return chore;
    }
}
