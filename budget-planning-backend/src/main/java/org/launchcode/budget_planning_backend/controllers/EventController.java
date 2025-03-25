package org.launchcode.budget_planning_backend.controllers;

import jakarta.validation.Valid;
import org.launchcode.budget_planning_backend.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@CrossOrigin
@RequestMapping(value ="/events")
public class EventController {

    private final Logger logger = LoggerFactory.getLogger(EventController.class);

    @GetMapping("/view")
    public ArrayList<Event> getEvents(){
        return new ArrayList<>();
    }

    @PostMapping("/create")
    public String createEvent(@Valid @RequestBody EventDTO eventDto){

        Event event = new Event(eventDto.getEventName(), eventDto.getEventBudget(), eventDto.getEventLocation(), eventDto.getEventDescription(),
                eventDto.getEventDate(), Status.OPEN, 0, new UserGroup());
        logger.info("Event created successfully".concat(event.toString()));
        return "Event Data";
    }

}
