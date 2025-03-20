package org.launchcode.budget_planning_backend.models;

import java.util.ArrayList;
import java.util.List;

public class Group extends AbstractEntity{

    private static int nextId = 1;

    private final List<User> users = new ArrayList<>();

    private final List<Event> events = new ArrayList<>();

    private  final List<ChoreDto> choreDTOS = new ArrayList<>();

    public Group(String name, String description){
        setName(name);
        setDescription(description);
        this.setId(nextId);
        nextId++;
    }

    public Group(){}

    public List<User> getUsers() {
        return users;
    }

    public List<Event> getEvents() {
        return events;
    }

    public List<ChoreDto> getChores() {
        return choreDTOS;
    }

    public void addUsers(User user){
        this.users.add(user);
    }

    public void addEvents(Event event){
        this.events.add(event);
    }

    public void addChores(ChoreDto chore){
        this.choreDTOS.add(chore);
    }
}
