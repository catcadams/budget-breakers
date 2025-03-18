package org.launchcode.budget_planning_backend.models;

import jakarta.persistence.Entity;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Group extends AbstractEntity{

    private final List<User> users = new ArrayList<>();

    private final List<Event> events = new ArrayList<>();

    private  final List<Chore> chores = new ArrayList<>();

    public Group(List<User> users, List<Event> events){
    }

    public Group(){}

    public List<User> getUsers() {
        return users;
    }

    public List<Event> getEvents() {
        return events;
    }

    public List<Chore> getChores() {
        return chores;
    }
}
