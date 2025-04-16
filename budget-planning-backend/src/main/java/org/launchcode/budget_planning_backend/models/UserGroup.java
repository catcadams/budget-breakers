package org.launchcode.budget_planning_backend.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
public class UserGroup extends AbstractEntity{

    @ManyToMany
    private final List<User> users = new ArrayList<>();

    // @JsonManagedReference
    @OneToMany(mappedBy = "userGroup", cascade = CascadeType.ALL)
    private final List<Event> events = new ArrayList<>();

    // @JsonManagedReference
    @OneToMany(mappedBy = "userGroup", cascade = CascadeType.ALL)
    private  final List<Chore> chores = new ArrayList<>();

    private final List<String> userEmails = new ArrayList<String>();

    public UserGroup(String name, String description){
        setName(name);
        setDescription(description);
    }

    public UserGroup(){}

    public List<User> getUsers() {
        return users;
    }

    public List<Event> getEvents() {
        return events;
    }

    public List<Chore> getChores() {
        return chores;
    }

//    public void setUsers(List<User> users) {
//        this.users = users;
//    }

    public void addUsers(User user){
        this.users.add(user);
    }

    public void addEvents(Event event){
        this.events.add(event);
    }

    public void addChores(Chore chore){
        this.chores.add(chore);
    }

    public void addEmails(String email) {
        this.userEmails.add(email);
    }

    public void removeUsers() {
        this.users.clear();
    }

    @Override
    public String toString() {
        return "UserGroup{" +
                " id=" + this.getId() +
                " name=" + this.getName() +
                " description=" + this.getDescription() +
                " users=" + users +
                " events=" + events +
//                " chores=" + chores +
                " emails=" + userEmails +
                "}";
    }
}
