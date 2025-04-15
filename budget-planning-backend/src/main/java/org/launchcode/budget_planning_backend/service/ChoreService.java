package org.launchcode.budget_planning_backend.service;

import jakarta.servlet.http.HttpServletRequest;
import org.launchcode.budget_planning_backend.controllers.AuthenticationController;
import org.launchcode.budget_planning_backend.data.ChoreRepository;
import org.launchcode.budget_planning_backend.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChoreService {

    @Autowired
    UserGroupService userGroupService;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    EventService eventService;

    @Autowired
    AuthenticationController authenticationController;

    @Autowired
    ChoreRepository choreRepository;

    private final Logger logger = LoggerFactory.getLogger(ChoreService.class);


    private final List<Chore> allChores = new ArrayList<>();
    private final List<Chore> choresByGroup = new ArrayList<>();

    public void saveChore(Chore chore) {
        choreRepository.save(chore);
        allChores.add(chore);
    }

    public Chore getChoreById(int id) {
        for(Chore chore : allChores){
            if(chore.getId() == id) {
                return chore;
            }
        }
        return null;
    }

    public Chore createNewChore(ChoreDto choreDto) {
        UserGroup group = userGroupService.getGroupByName(choreDto.getUserGroupName());
        Chore chore = new Chore(choreDto.getName(), choreDto.getDescription(), choreDto.getAmountOfEarnings());
        chore.setStatus(Status.OPEN);
        chore.setGroup(group);
        logger.info("New Chore created: ".concat(chore.toString()));
        return chore;
    }

    public List<Chore> getChoresByGroup(int userGroupId) {
        choresByGroup.clear();
        for (Chore chore : allChores) {
            if (chore.getGroup().getId() == userGroupId) {
                choresByGroup.add(chore);
            }
        } return choresByGroup;
    }

    public void updateChoreDetailsByChoreId(int choreId, ChoreDto choreDto) {
        Chore chore = getChoreById(choreId);
        chore.setName(choreDto.getName());
        chore.setDescription(choreDto.getDescription());
        chore.setAmountOfEarnings(choreDto.getAmountOfEarnings());
    }

    public Chore assignChoreToTheUser(int choreId, HttpServletRequest request) {
//        User user = authenticationService.getCurrentUser(request);
        User user = authenticationController.getUserFromSession(request.getSession());
        Chore chore = getChoreById(choreId);
        chore.setStatus(Status.IN_PROGRESS);
        chore.setUser(user);
        logger.info("The chore was assigned to the user:" + chore.toString());
        return chore;
    }

    public Chore unassignChore(int choreId, HttpServletRequest request) {
//        User currentUser = authenticationService.getCurrentUser(request);
        User currentUser = authenticationController.getUserFromSession(request.getSession());
        Chore chore = getChoreById(choreId);
        if(chore.getUser().getId() == currentUser.getId()){
            chore.setStatus(Status.OPEN);
            chore.setUser(null);
            logger.info("The chore was unassigned:" + chore.toString());
        }
        return chore;
    }

    public Chore completeChoreByMinor(int choreId, int eventId, int groupId, HttpServletRequest request) {
        Chore chore = getChoreById(choreId);
        Event selectedEvent = eventService.getEventForGroup(authenticationService.getCurrentUser(request), groupId,
                eventId);
        chore.setStatus(Status.PENDING);
        chore.setEvent(selectedEvent);
        logger.info("Updating chore details after marking the chore as Pending: "+ chore.toString());
        return chore;
    }

    public Chore confirmChoreByAdult(int choreId) {
        Chore chore = getChoreById(choreId);
        chore.setStatus(Status.COMPLETE);
        Contributions contributions = new Contributions();
        contributions.setAmountOfContribution(chore.getAmountOfEarnings());
        contributions.setDate(LocalDate.now());
        contributions.setUser(chore.getUser());
        contributions.setStatus(Status.COMPLETE);
        contributions.setEvent(chore.getEvent());
        eventService.addContributionAfterChoreCompletion(contributions);
        logger.info("New contribution generated due the chore completion: "+ contributions.toString());
        return chore;
    }

    public void deleteChoreById(int choreId) {
        Chore choreToDelete = null;
        for(Chore chore : allChores){
            if(chore.getId() == choreId) {
                choreToDelete = chore;
                logger.info("Deleted the chore with Id={}", choreId);
            }
        }
        if(choreToDelete != null) {
            allChores.remove(choreToDelete);
            logger.info("Deleted the chore with Id={}", choreId);
        } else logger.info("There is no chore with given Id. Unable to delete");
    }

    public Chore rejectChoreByAdult(int choreId) {
        Chore chore = getChoreById(choreId);
        chore.setStatus(Status.OPEN);
        chore.setEvent(null);
        chore.setUser(null);
        return chore;
    }
}

