package org.launchcode.budget_planning_backend.service;

import org.launchcode.budget_planning_backend.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {

    @Autowired
    UserGroupService userGroupService;

    private final Logger logger = LoggerFactory.getLogger(EventService.class);

    public void createEvent(EventDTO eventDto) {
        UserGroup group = userGroupService.getGroupByName(eventDto.getUserGroupName());
        Event event = new Event(eventDto.getEventName(), eventDto.getEventBudget(), eventDto.getEventLocation(), eventDto.getEventDescription(),
                eventDto.getEventDate(), Status.OPEN, 0,group);
        event.setUserGroup(group);
        group.addEvents(event);
        logger.info("Event Created Successfully".concat(event.toString()));
    }

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
                contributionDto.setName(contribution.getUser().getFirstName()+ contribution.getUser().getLastName());
                contributions.add(contributionDto);
            }
        }
        return  contributions;
    }
}
