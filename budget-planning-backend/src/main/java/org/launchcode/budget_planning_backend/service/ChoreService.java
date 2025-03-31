package org.launchcode.budget_planning_backend.service;

import org.launchcode.budget_planning_backend.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.launchcode.budget_planning_backend.models.DummyObjectsToBeDeleted.getGroupByName;

@Service
public class ChoreService {

    private final Logger logger = LoggerFactory.getLogger(ChoreService.class);


    private final List<Chore> allChores = new ArrayList<>();
    private final List<Chore> choresByGroup = new ArrayList<>();

    public void saveChore(Chore chore) {
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

    public List<Chore> getAllChores() {
        return allChores;
    }

    public Chore createNewChore(ChoreDto choreDto) {
        Chore chore = new Chore(choreDto.getName(), choreDto.getDescription(), choreDto.getAmountOfEarnings());
        //temp userGroup handling, will be replaced after Groups controllers are implemented
        UserGroup group = getGroupByName(choreDto.getUserGroupName());
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

// TODO : add ability to change user group once user groups are live in app and groups have user members defined. No
//  group update as of now.
    public void updateChoreDetailsByChoreId(int choreId, ChoreDto choreDto) {
        Chore chore = getChoreById(choreId);
        chore.setName(choreDto.getName());
        chore.setDescription(choreDto.getDescription());
        chore.setAmountOfEarnings(choreDto.getAmountOfEarnings());
    }

    public Chore assignChoreToTheUser(int choreId) {
        //setting dummy user for now to test the functionality
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate localDate = LocalDate.of(2016, 8, 19);
        Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
        User dummyUser = new User("John", "Smith", date, "test@gmail.com", "test", "1234", "1234");
        dummyUser.setAccountType(AccountType.MINOR);
        Chore chore = getChoreById(choreId);
        chore.setStatus(Status.IN_PROGRESS);
        chore.setUser(dummyUser);
        logger.info("The chore was assigned to the user:" + chore.toString());
        return chore;
    }

    public Chore completeChoreByMinor(int choreId) {
        Chore chore = getChoreById(choreId);
        Event dummyEvent = new Event(); //to be replaced with real Event as user selection on UI
        dummyEvent.setName("Movie Frozen 2");
        dummyEvent.setLocation("St. Louis");
        chore.setStatus(Status.PENDING);
        chore.setEvent(dummyEvent);
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
}

