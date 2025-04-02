package org.launchcode.budget_planning_backend.controllers;

import jakarta.validation.Valid;
import org.launchcode.budget_planning_backend.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value ="/events")
public class EventController {

    private final Logger logger = LoggerFactory.getLogger(EventController.class);

    public static User user = new User();
    public static UserGroup group = new UserGroup(1,"Test");
    public static boolean isGroupSet = false;

    @GetMapping("/{userGroupId}/list")
    public List<Event> getEvents(@PathVariable int userGroupId){
        logger.info("Inside GetEvents");
        return group.getEvents();
    }

    @PostMapping("/create")
    public void createEvent(@Valid @RequestBody EventDTO eventDto){
        if(!isGroupSet) {
            user.setAccountType(AccountType.ADULT);
            user.addUserGroup(group);
        }
        Event event = new Event(eventDto.getEventName(), eventDto.getEventBudget(), eventDto.getEventLocation(), eventDto.getEventDescription(),
                eventDto.getEventDate(), Status.OPEN, 0, group);
        group.addEvents(event);
        logger.info("Event created successfully".concat(event.toString()));
    }

    @GetMapping("/{userGroupId}/{eventId}")
    public EventDTO viewEventDetails(@PathVariable int userGroupId, @PathVariable Integer eventId) {
        logger.info("Inside viewEventDetails ");
        List<Event> events = group.getEvents();
        EventDTO eventDto = new EventDTO();
        for(Event event: events){
            if(event.getId() == eventId){
                eventDto.setEventName(event.getName());
                eventDto.setEventEarnings(event.getEarnings());
                eventDto.setEventBudget((event.getBudget()));
                eventDto.setEventDescription(event.getDescription());
                eventDto.setEventDate(  event.getDate().toString());
                eventDto.setEventLocation(event.getLocation());
                return eventDto;

            }
        }
        return null;
    }
    @PostMapping("/{userGroupId}/{eventId}/edit")
    public String editEventDetails(@Valid @RequestBody EventDTO eventDto, @PathVariable int userGroupId, @PathVariable Integer eventId) {
        logger.info("Inside editEventDetails ");
        List<Event> events = group.getEvents();
        for(Event event: events){
            if(event.getId() == eventId) {
                event.setName(eventDto.getEventName());
                event.setDescription(eventDto.getEventDescription());
                event.setBudget(eventDto.getEventBudget());
                event.setLocation(eventDto.getEventLocation());
                event.setDate(LocalDate.parse(eventDto.getEventDate()));
                event.setEarnings(eventDto.getEventEarnings());
                return "Event updated Successfully";
            }
        }
        return "Update Event failed";
    }


}
