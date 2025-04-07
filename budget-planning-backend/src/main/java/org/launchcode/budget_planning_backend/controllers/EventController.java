package org.launchcode.budget_planning_backend.controllers;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.launchcode.budget_planning_backend.models.*;
import org.launchcode.budget_planning_backend.service.UserGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.launchcode.budget_planning_backend.controllers.AuthenticationController;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value ="/events")
public class EventController {

    @Autowired
    AuthenticationController authenticationController;

    @Autowired
    UserGroupService groupService;

    private final Logger logger = LoggerFactory.getLogger(EventController.class);

    UserGroup userGroup;

    public static User user = new User("Cat", "Adams", LocalDate.now(), "cat@cat.com", "catadams", "password", "password");


    public static UserGroup group = new UserGroup(1,"Test");
    public static boolean isGroupSet = false;

    @GetMapping("/{userGroupId}/list")
    public ResponseEntity<List<Event>> getEvents(@PathVariable int userGroupId, HttpServletRequest request){
        logger.info("Inside GetEvents");
        User user = authenticationController.getUserFromSession(request.getSession(false));
        group = groupService.getGroupByID(userGroupId);
        logger.info("User has access to group: " + groupService.hasAccessToGroup(userGroupId, user.getId()));
        if (groupService.hasAccessToGroup(userGroupId, user.getId())) {
            logger.info("Events for group: " + userGroupId + group.getEvents());
            return ResponseEntity.ok(group.getEvents());
        }
        return ResponseEntity.badRequest().body(null);
    }

    @PostMapping("/create")
    public void createEvent(@Valid @RequestBody EventDTO eventDto){
        if(!isGroupSet) {
            user.setAccountType(AccountType.ADULT);
            user.addUserGroup(group);
            isGroupSet = true;
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
                eventDto.setEventId(eventId);
                eventDto.setEventName(event.getName());
                eventDto.setEventEarnings(event.getEarnings());
                eventDto.setEventBudget((event.getBudget()));
                eventDto.setEventDescription(event.getDescription());
                if(event.getDate() == null)eventDto.setEventDate( ""); else eventDto.setEventDate(  event.getDate().toString());
                eventDto.setEventLocation(event.getLocation());
                return eventDto;

            }
        }
        return null;
    }
    @PutMapping("/edit/{userGroupId}/{eventId}")
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

    @PostMapping("/contribute/{userGroupId}/{eventId}")
    public void addContribution(@Valid @RequestBody ContributionDTO contributionDTO, @PathVariable int userGroupId, @PathVariable int eventId){
        logger.info("Inside Contribute");
        // Need to implement get group with groupID
        List<Event> events = group.getEvents();
        Event event = null;
        Contributions contributions = new Contributions();


        //Get the event
        for(Event eventDetail: events){
            if(eventDetail.getId() == eventId){
                event = eventDetail;
                break;
            }
        }
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
            contributions.setStatus(Status.COMPLETE);
            event.addContributions(contributions);
            contributions.setEventID(eventId);
            //contributions.setEvent(event);
        }
        logger.info("Event created successfully".concat(contributions.toString()));
    }


}
