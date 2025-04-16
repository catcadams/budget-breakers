package org.launchcode.budget_planning_backend.service;

import jakarta.transaction.Transactional;
import org.launchcode.budget_planning_backend.data.EventRepository;
import org.launchcode.budget_planning_backend.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * This class has all the service methods/functions needed to create and manage an Event
 * which can be a part of any group (A group of family members/friends/both).
 */
@Service
public class EventService {

    @Autowired
    UserGroupService userGroupService;

    @Autowired
    EventRepository eventRepository;

    private final Logger logger = LoggerFactory.getLogger(EventService.class);

    /**
     * Creates an Event object with the specified fields from the "Create Event" form
     * @param eventDto the object that has the data for the creation of an event
     */
    public void createEvent(EventDTO eventDto) {
        UserGroup group = userGroupService.getGroupByName(eventDto.getUserGroupName());
        Event event = new Event(eventDto.getEventName(), eventDto.getEventBudget(), eventDto.getEventLocation(), eventDto.getEventDescription(),
                eventDto.getEventDate(), Status.OPEN, 0,group);
        event.setUserGroup(group);
        event.setBudgetReached(false);
        group.addEvents(event);
        eventRepository.save(event);
        logger.info("Event Created Successfully".concat(event.toString()));
    }

    /**
     * Fetches the list of Events object associated with the specific group
     * @param userGroupId the group ID of the group the event belongs to.
     * @param user the current user logged in
     */
    public List<Event> getEvents(int userGroupId, User user){
        logger.info("User has access to group: " + userGroupService.hasAccessToGroups(userGroupId, user.getId()));
        List<Event> eventsList = new ArrayList<>();
       if (userGroupService.hasAccessToGroups(userGroupId, user.getId())) {
            eventsList =userGroupService.getEventsFromGroup(userGroupId);
            logger.info("Events for group: " + userGroupId + eventsList);
           return eventsList;
        }
        return eventsList;
    }

    /**
     * Fetches an Event object with the event ID
     * @param userGroupId the group ID of the group the event belongs to.
     * @param user the current user logged in
     * @param eventId the Id of the event to be fetched
     */
    public Event getEventForGroup(User user, int userGroupId, int eventId){
        List<Event> events = getEvents(userGroupId, user);
        Event event = null;
        //Get the event
        for(Event eventDetail: events){
            if(eventDetail.getId() == eventId){
                event = eventDetail;
                break;
            }
        }
        return event;
    }

    /**
     * Sets the eventDto object for an Event object
     * @param event the event Object
     * @param eventDto the eventDto object which transfers data to/from the frontend page
     */
    public void setEventDtoForEvent(Event event, EventDTO eventDto){
        eventDto.setEventId(event.getId());
        eventDto.setEventName(event.getName());
        eventDto.setEventEarnings(event.getEarnings());
        eventDto.setEventBudget((event.getBudget()));
        eventDto.setEventDescription(event.getDescription());
        if(event.getDate() == null)eventDto.setEventDate( ""); else eventDto.setEventDate(  event.getDate().toString());
        eventDto.setEventLocation(event.getLocation());
        eventDto.setUserGroupName(event.getUserGroup().getName());
        eventDto.setBudgetAchieved(event.isBudgetReached());
    }

    /**
     * Saves the updated event object to the repository
     * @param event the event Object
     * @param eventDto the eventDto object which transfers data to/from the frontend page
     */
    public void updateEvent(Event event, EventDTO eventDto){
        event.setName(eventDto.getEventName());
        event.setDescription(eventDto.getEventDescription());
        event.setBudget(eventDto.getEventBudget());
        event.setLocation(eventDto.getEventLocation());
        if(!eventDto.getEventDate().isBlank()) {
            event.setDate(LocalDate.parse(eventDto.getEventDate()));
        }
        event.setEarnings(eventDto.getEventEarnings());
        isBudgetReachedForEvent(event);
        eventRepository.save(event);
    }

    /**
     * Updates the Event object's STATUS of the passed event.
     *  Event Created: OPEN, Event started with contributions: IN_PROGRESS, Event Achieved budget: COMPLETE
     * @param event the event Object
     */
    public void setEventStatus(Event event){
        //Set Status
        if (event.getStatus() == Status.OPEN) {
            event.setStatus(Status.IN_PROGRESS);
        }
        // event completion
        if (event.getEarnings() == event.getBudget()) {
            event.setStatus(Status.COMPLETE);
        }
    }

    /**
     * Updates the Contribution object's STATUS :
     *  Contribution by ADULT user : COMPLETE , Contribution by CHILD user: PENDING (Changes to APPROVED
     *  when an ADULT user approves it.
     * @param user the current user who contributed for the event
     * @param contributions the Contribution Object added for an event
     */
    public void setContributionStatus(User user, Contributions contributions){
        // based on the user type - set the status to "PENDING" - child, "COMPLETED" - ADULT"
        if(user.getAccountType().equals(AccountType.ADULT)) {
            contributions.setStatus(Status.COMPLETE);
        }else{
            contributions.setStatus(Status.PENDING);
        }
    }

    /**
     * Add a Contribution to the event by current user.
     * @param user the current user who contributed for the event
     * @param event the event to which the contribution was made for
     * @param amountOfContribution the amount contributed by the user to this event
     */
    public Contributions addContributionToEvent(User user, Event event, double amountOfContribution){
        if(!user.getAccountType().equals(AccountType.MINOR)){
            event.setEarnings(event.getEarnings() + amountOfContribution);
        }
        logger.info(event.toString());
        Contributions contributions = new Contributions();
        // add Contribution to an Event
        contributions.setDate(LocalDate.now());
        contributions.setAmountOfContribution(amountOfContribution);
        contributions.setUser(user);
        setContributionStatus(user, contributions);
        // Set event status
        isBudgetReachedForEvent(event);
        event.addContributions(contributions);
        setEventStatus(event);
        saveEventWithContribution(event);
        return contributions;
    }

    /**
     * Fetches all the contributions made for the event in the specific group
     * @param user the current user
     * @param userGroupId the group the event is part of
     * @param eventId the event ID of the event whose contribution is fetched
     */
    public List<ContributionDTO> getContributionsForEvent(User user,int userGroupId, int eventId){
        List<ContributionDTO> contributions = new ArrayList<>();
        List<Event> events = getEvents(userGroupId, user);
        List<Contributions> listOfContributions = null;
        // get the contributions for an event
        for(Event eventDetail: events){
            if(eventDetail.getId() == eventId) {
                listOfContributions = eventDetail.getContributions();
                break;
            }
        }
        // If there are no contributions so far, send an empty list
        if(listOfContributions == null || listOfContributions.isEmpty()){
            return  contributions;
        }else {
            //Set DTO with contribution Details to show in contribution table
            ContributionDTO contributionDto = null;
            for (Contributions contribution : listOfContributions) {
                contributionDto = new ContributionDTO();
                contributionDto.setId(contribution.getId());
                contributionDto.setDate(contribution.getDate());
                contributionDto.setStatus(contribution.getStatus());
                contributionDto.setAmountOfContribution(contribution.getAmountOfContribution());
                contributionDto.setUser(contribution.getUser());
                contributionDto.setName(contribution.getUser().getFirstName().concat(" ").concat(contribution.getUser().getLastName()));
                contributions.add(contributionDto);
            }
        }
        return  contributions;
    }

    /**
     * Fetches a specific contribution made for an event
     * @param event the event the contribution was made for
     * @param contributionId the id of the contribution being fetched
     */
    public Contributions getContribution(Event event, int contributionId){
            List<Contributions> contributions = event.getContributions();
            for(Contributions contribution: contributions){
                if(contribution.getId() == contributionId){
                    return contribution;
                }
            }
            return null;
    }

    /**
     * Delete the event and its corresponding contributions
     * @param user the current user
     * @param userGroupId the group the event is part of
     * @param eventId the event ID of the event to be deleted
     */
    public void deleteEvent(User user, int userGroupId, int eventId){
        Event event = getEventForGroup(user, userGroupId, eventId);
        List<Chore> choresInGroup = userGroupService.getChoresFromGroup(userGroupId);
        for(Chore chore : choresInGroup) {
            if(chore.getEvent() == event) {
                chore.setEvent(null);
            }
        }
        userGroupService.getEventsFromGroup(userGroupId).remove(event);
        eventRepository.delete(event);
    }

    /**
     * Checks if the Budget for the event is achieved or not, and sets the event with the result.
     * @param event the event object
     */
    public void isBudgetReachedForEvent(Event event){
        event.setBudgetReached(event.getEarnings() == event.getBudget() || event.getEarnings() > event.getBudget());
    }

    /**
     * Adds Contribution through Chore completion
     * @param contributions the contribution object
     */
    public void addContributionAfterChoreCompletion(Contributions contributions){
        Event event = contributions.getEvent();
        event.setEarnings(event.getEarnings() + contributions.getAmountOfContribution());
        isBudgetReachedForEvent(event);
        event.addContributions(contributions);
        setEventStatus(contributions.getEvent());
        saveEventWithContribution(event);
        logger.info("Added contribution to the event:" + contributions.getEvent().toString());
    }

    /**
     * Sets the EventDto list to send list of events
     * @param events the list of events
     * @param eventDtos the eventDto list to be sent back to frontend
     */
    public void setEventDtoList(List<Event> events, List<EventDTO> eventDtos){
        for(Event event: events){
            EventDTO eventDTO = new EventDTO();
            setEventDtoForEvent(event, eventDTO);
            eventDtos.add(eventDTO);
        }
    }

    /**
     * Save the event and its contributions to the repository
     * @param event the event object to be saved
     */
    @Transactional
    public void saveEventWithContribution(Event event) {
        eventRepository.save(event);
    }
}
