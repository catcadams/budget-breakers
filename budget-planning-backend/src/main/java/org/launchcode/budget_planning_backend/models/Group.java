package org.launchcode.budget_planning_backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Group extends AbstractEntity{

    @ManyToMany
    private final List<User> users = new ArrayList<>();

    @OneToMany
    private final List<Event> events = new ArrayList<>();

    @OneToMany
    private final List<Chore> chores = new ArrayList<>();
    private  final List<ChoreDto> choreDTOS = new ArrayList<>();

    public Group(String name, String description){
        setName(name);
        setDescription(description);
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
