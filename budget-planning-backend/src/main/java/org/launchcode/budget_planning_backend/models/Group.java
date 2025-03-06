package org.launchcode.budget_planning_backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Group extends AbstractEntity{

    @ManyToMany(mappedBy = "group")
    private final List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "group")
    private final List<Event> events = new ArrayList<>();

    public Group(){}
}
