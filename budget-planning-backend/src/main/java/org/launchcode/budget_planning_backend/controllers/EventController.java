package org.launchcode.budget_planning_backend.controllers;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.launchcode.budget_planning_backend.models.*;
import org.launchcode.budget_planning_backend.service.AuthenticationService;
import org.launchcode.budget_planning_backend.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value ="/events")
public class EventController {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    EventService eventService;

    private final Logger logger = LoggerFactory.getLogger(EventController.class);

    @GetMapping("/{userGroupId}/list")
    public ResponseEntity<List<Event>> getEvents(@PathVariable int userGroupId, HttpServletRequest request){
        logger.info("Inside GetEvents");
        User user = authenticationService.getCurrentUser(request);
        List<Event> listOfEvents = eventService.getEvents(userGroupId, user);
        if (listOfEvents.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(Collections.emptyList());
        }
        return ResponseEntity.ok(listOfEvents);
    }

    @PostMapping("/create")
    public void createEvent(@Valid @RequestBody EventDTO eventDto){
        eventService.createEvent(eventDto);
        logger.info("Event created successfully");
    }

    @GetMapping("/{userGroupId}/{eventId}")
    public EventDTO viewEventDetails(@PathVariable int userGroupId, @PathVariable Integer eventId, HttpServletRequest request) {
        logger.info("Inside viewEventDetails ");
        User user = authenticationService.getCurrentUser(request);
        List<Event> events = eventService.getEvents(userGroupId, user);
        EventDTO eventDto = new EventDTO();
        for(Event event: events){
            if(event.getId() == eventId){
                eventDto.setEventId(eventId);
                eventDto.setEventName(event.getName());
                eventDto.setEventEarnings(event.getEarnings());
                eventDto.setEventBudget((event.getBudget()));
                eventDto.setEventDescription(event.getDescription());
                if(event.getDate() == null)eventDto.setEventDate( ""); else eventDto.setEventDate(  event.getDate().toString());
                eventDto.setEventLocation(event.getLocation());
                eventDto.setUserGroupName(event.getUserGroup().getName());
                return eventDto;

            }
        }
        return null;
    }
    @PutMapping("/edit/{userGroupId}/{eventId}")
    public String editEventDetails(@Valid @RequestBody EventDTO eventDto, @PathVariable int userGroupId, @PathVariable Integer eventId, HttpServletRequest request) {
        logger.info("Inside editEventDetails ");
        User user = authenticationService.getCurrentUser(request);
        List<Event> events = eventService.getEvents(userGroupId, user);
        for(Event event: events){
            if(event.getId() == eventId) {
                event.setName(eventDto.getEventName());
                event.setDescription(eventDto.getEventDescription());
                event.setBudget(eventDto.getEventBudget());
                event.setLocation(eventDto.getEventLocation());
                if(!eventDto.getEventDate().isBlank()) {
                    event.setDate(LocalDate.parse(eventDto.getEventDate()));
                }
                event.setEarnings(eventDto.getEventEarnings());
                return "Event updated Successfully";
            }
        }
        return "Update Event failed";
    }

    @PostMapping("/contribute/{userGroupId}/{eventId}")
    public String addContribution(@Valid @RequestBody ContributionDTO contributionDTO, @PathVariable int userGroupId, @PathVariable int eventId, HttpServletRequest request){
        logger.info("Inside Contribute");
        // Need to implement get group with groupID
        User user = authenticationService.getCurrentUser(request);
        Event event = eventService.getEventForGroup(user, userGroupId, eventId);
        Contributions contributions = new Contributions();

        //Set Contribution details to event
        if(event !=null) {
            event.setEarnings(event.getEarnings() + contributionDTO.getAmountOfContribution());
            logger.info(event.toString());
            //Set Status
            if (event.getStatus() == Status.OPEN) {
                event.setStatus(Status.IN_PROGRESS);
            }
            // event completion
            if (event.getEarnings() == event.getBudget()) {
                event.setStatus(Status.COMPLETE);
            }
            contributions.setDate(LocalDate.now());
            //contributions.setEvent(event);
            contributions.setAmountOfContribution(contributionDTO.getAmountOfContribution());
            contributions.setUser(user); //contributions.setUser(); - implementation pending!
            // based on the user - set the status to "PENDING" - child, "COMPLETED" - ADULT" - implementation pending!
            if(user.getAccountType().equals(AccountType.ADULT)) {
                contributions.setStatus(Status.COMPLETE);
            }else{
                contributions.setStatus(Status.PENDING);
            }
            event.addContributions(contributions);
            contributions.setEventID(eventId);
            contributions.setEvent(event);
        }
        logger.info("Contributed Successfully".concat(contributions.toString()));
        return "Contribution added successfully";
    }

    @GetMapping("/contributions/{userGroupId}/{eventId}")
    public List<ContributionDTO> getContributions(@PathVariable int userGroupId, @PathVariable int eventId, HttpServletRequest request) {
        User user = authenticationService.getCurrentUser(request);
        return eventService.getContributionsForEvent(user, userGroupId, eventId);
    }
}
