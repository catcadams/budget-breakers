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
import java.util.Optional;

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


    public Chore getChoreById(int id) {
        Optional<Chore> choreOptional = choreRepository.findById(id);
        return choreOptional.orElse(null);
    }

    public Chore createNewChore(ChoreDto choreDto) {
        UserGroup group = userGroupService.getGroupByName(choreDto.getUserGroupName());
        Chore chore = new Chore(choreDto.getName(), choreDto.getDescription(), choreDto.getAmountOfEarnings());
        chore.setStatus(Status.OPEN);
        chore.setGroup(group);
        group.addChores(chore);
        choreRepository.save(chore);
        logger.info("New Chore created: ".concat(chore.toString()));
        return chore;
    }

    public List<ChoreDto> getChoresByGroup(int userGroupId) {
        List<Chore> chores = (List<Chore>) choreRepository.findAll();
        List<ChoreDto> dtoList = new ArrayList<>();
        for (Chore chore : chores) {
            if (chore.getGroup() != null && chore.getGroup().getId() == userGroupId) {
                ChoreDto dto = new ChoreDto();
                dto.setId(chore.getId());
                dto.setName(chore.getName());
                dto.setAmountOfEarnings(chore.getAmountOfEarnings());
                dto.setUserGroup(chore.getGroup());
                dto.setUserGroupName(chore.getGroup().getName());
                dto.setStatus(chore.getStatus());
                dtoList.add(dto);
            }
        }
        return dtoList;
    }

    public void updateChoreDetailsByChoreId(int choreId, ChoreDto choreDto) {
        Chore chore = getChoreById(choreId);
        chore.setName(choreDto.getName());
        chore.setDescription(choreDto.getDescription());
        chore.setAmountOfEarnings(choreDto.getAmountOfEarnings());
        choreRepository.save(chore);
    }

    public Chore assignChoreToTheUser(int choreId, HttpServletRequest request) {
        User user = authenticationController.getUserFromSession(request.getSession());
        Chore chore = getChoreById(choreId);
        chore.setStatus(Status.IN_PROGRESS);
        chore.setUser(user);
        choreRepository.save(chore);
        logger.info("The chore was assigned to the user:" + chore.toString());
        return chore;
    }

    public Chore unassignChore(int choreId, HttpServletRequest request) {
//        User currentUser = authenticationService.getCurrentUser(request);
        //User currentUser = authenticationController.getUserFromSession(request.getSession());
        Chore chore = getChoreById(choreId);
        chore.setStatus(Status.OPEN);
        chore.setUser(null);
        choreRepository.save(chore);
        logger.info("The chore was unassigned:" + chore.toString());
        return chore;

    }

    public Chore completeChoreByMinor(int choreId, int eventId, int groupId, HttpServletRequest request) {
        Chore chore = getChoreById(choreId);
        User currentUser = authenticationController.getUserFromSession(request.getSession());
        Event selectedEvent = eventService.getEventForGroup(currentUser, groupId, eventId);
        chore.setStatus(Status.PENDING);
        chore.setEvent(selectedEvent);
        choreRepository.save(chore);
        logger.info("Updating chore details after marking the chore as Pending: " + chore.toString());
        return chore;
    }

    public Chore confirmChoreByAdult(int choreId) {
        Chore chore = getChoreById(choreId);
        chore.setStatus(Status.COMPLETE);
        choreRepository.save(chore);
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
        if (choreRepository.existsById(choreId)) {
            choreRepository.deleteById(choreId);
            logger.info("Deleted the chore with Id={}", choreId);
        } else {
            logger.info("There is no chore with given Id. Unable to delete");
        }
    }

    public Chore rejectChoreByAdult(int choreId) {
        Chore chore = getChoreById(choreId);
        chore.setStatus(Status.OPEN);
        chore.setEvent(null);
        chore.setUser(null);
        choreRepository.save(chore);
        return chore;
    }
}

