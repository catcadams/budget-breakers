package org.launchcode.budget_planning_backend.controllers;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.launchcode.budget_planning_backend.models.*;
import org.launchcode.budget_planning_backend.service.EventService;
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
@CrossOrigin
@RequestMapping(value ="/events")
public class EventController {

    @Autowired
    AuthenticationController authenticationController;

    @Autowired
    EventService eventService;

    private final Logger logger = LoggerFactory.getLogger(EventController.class);

    /**
     * Retrieves the list of events
     *
     * @param groupID The group id for which the events are fetched
     * @return The list of event objects if found, or Empty list if not found.
     */
    @GetMapping("/{groupID}/list")
    public ResponseEntity<List<EventDTO>> getEvents(@PathVariable Integer groupID, HttpServletRequest request){
        logger.info("Inside GetEvents");
        User user = authenticationController.getUserFromSession(request.getSession());
        List<Event> listOfEvents = eventService.getEvents(groupID, user);
        List<EventDTO> listOfEventsDto = new ArrayList<>();
        if (listOfEvents.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(Collections.emptyList());
        }else{
            eventService.setEventDtoList(listOfEvents, listOfEventsDto);
        }
        return ResponseEntity.ok(listOfEventsDto);
    }

    /**
     * Creates an Event for the selected Group
     *
     * @param eventDto the object that has the data for the creation of an event
     */
    @PostMapping("/create")
    public void createEvent(@Valid @RequestBody EventDTO eventDto){
        eventService.createEvent(eventDto);
        logger.info("Event created successfully");
    }

    /**
     * Fetches the event details of a selected Event
     *
     * @param userGroupId The group id for which the event is fetched
     * @param eventId the event id of the event fetched
     * @return The eventDto object with the event object details.
     */
    @GetMapping("/{userGroupId}/{eventId}")
    public EventDTO viewEventDetails(@PathVariable Integer userGroupId, @PathVariable Integer eventId, HttpServletRequest request) {
        logger.info("Inside viewEventDetails ");
        User user = authenticationController.getUserFromSession(request.getSession());
        List<Event> events = eventService.getEvents(userGroupId, user);
        EventDTO eventDto = new EventDTO();
        for(Event event: events){
            if(event.getId() == eventId){
                eventService.setEventDtoForEvent(event, eventDto);
            }
        }
        logger.info("Event fetched successfully".concat(eventDto.toString()));
        return eventDto;
    }

    /**
     * Updates the event details of a selected Event
     *
     * @param eventDto the updated details of the event
     * @param userGroupId The group id for which the event is updated
     * @param eventId the event id of the event to be updated
     * @return Success /Failed message
     */
    @PutMapping("/edit/{userGroupId}/{eventId}")
    public String editEventDetails(@Valid @RequestBody EventDTO eventDto, @PathVariable Integer userGroupId, @PathVariable Integer eventId, HttpServletRequest request) {
        logger.info("Inside editEventDetails ");
        User user = authenticationController.getUserFromSession(request.getSession());
        List<Event> events = eventService.getEvents(userGroupId, user);
        for(Event event: events){
            if(event.getId() == eventId) {
                eventService.updateEvent(event, eventDto);
                return "Event updated Successfully";
            }
        }
        return "Event Update failed";
    }

    /**
     * Add a contribution the selected event
     *
     * @param contributionDTO the object that has the contribution details
     * @param userGroupId The group id of the event
     * @param eventId the event id of the event to which the contribution is made
     * @return Success /Failed message
     */
    @PostMapping("/contribute/{userGroupId}/{eventId}")
    public String addContribution(@Valid @RequestBody ContributionDTO contributionDTO, @PathVariable Integer userGroupId, @PathVariable Integer eventId, HttpServletRequest request){
        logger.info("Inside Contribute");
        Contributions contributions = null;
        User user = authenticationController.getUserFromSession(request.getSession());
        Event event = eventService.getEventForGroup(user, userGroupId, eventId);

        //Set Contribution details to event
        if(event !=null) {
            contributions = eventService.addContributionToEvent(user, event, contributionDTO.getAmountOfContribution());
            logger.info("Contributed Successfully".concat(contributions.toString()));
        }
        return "Contribution added successfully";
    }

    /**
     * Fetches all the contributions made to the selected event
     *
     * @param userGroupId The group id of the event
     * @param eventId the event id of the event to which the contributions were made
     */
    @GetMapping("/contributions/{userGroupId}/{eventId}")
    public List<ContributionDTO> getContributions(@PathVariable Integer userGroupId, @PathVariable Integer eventId, HttpServletRequest request) {
        User user = authenticationController.getUserFromSession(request.getSession());
        return eventService.getContributionsForEvent(user, userGroupId, eventId);
    }

    /**
     * Approve a contribution made by a child user to the selected event
     *
     * @param userGroupId The group id of the event
     * @param eventId the event id of the event to which the contributions were made
     * @param contributionId the contribution id of the contribution made by the child user
     */
    @PostMapping("/approveContribution/{userGroupId}/{eventId}/{contributionId}")
    public void approveContribution(@PathVariable Integer userGroupId, @PathVariable Integer eventId, @PathVariable Integer contributionId, HttpServletRequest request){
       logger.info("Inside approveContribution");
        User user = authenticationController.getUserFromSession(request.getSession());
        Event event = eventService.getEventForGroup(user, userGroupId, eventId);
        Contributions contribution = eventService.getContribution(event, contributionId);
        contribution.setStatus(Status.COMPLETE);
        event.setEarnings(event.getEarnings() + contribution.getAmountOfContribution());
        eventService.isBudgetReachedForEvent(event);
        eventService.saveEventWithContribution(event);
        logger.info("Contribution approved successfully");
    }

    /**
     * Delete the selected Event and all its contributions
     *
     * @param userGroupId The group id of the event
     * @param eventId the event id of the event
     */
    @DeleteMapping("/delete/{userGroupId}/{eventId}")
    public void deleteEvent(@PathVariable Integer userGroupId, @PathVariable Integer eventId, HttpServletRequest request){
        logger.info("Inside delete Event");
        User user = authenticationController.getUserFromSession(request.getSession());
        eventService.deleteEvent(user, userGroupId, eventId);
        logger.info("Event deleted successfully! EventId: "+ eventId +"from the group Group ID: "+ userGroupId +" by the user Name: "+ user.getFirstName());
    }

}
