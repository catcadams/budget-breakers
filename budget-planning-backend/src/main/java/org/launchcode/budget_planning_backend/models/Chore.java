package org.launchcode.budget_planning_backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Chore extends AbstractEntity{

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    @ManyToOne
    private Event event;

    @OneToOne(cascade = CascadeType.ALL)
    private Contributions contribution;

    @ManyToOne
    @NotBlank(message = "A group selection is required.")
    private Group group;

    private String status;

    @NotBlank(message = "Amount of earnings for this chore is required.")
    private Double amountOfEarnings;

    public Chore(String name, String description, Double amountOfEarnings) {
        setName(name);
        setDescription(description);
        this.amountOfEarnings = amountOfEarnings;
    }

    public Chore(){}

    public User getUser() {return user;}

    public void setUser(User user) {this.user = user;}

    public Event getEvent() {return event;}

    public void setEvent(Event event) {this.event = event;}

    public Contributions getContribution() {return contribution;}

    public void setContribution(Contributions contribution) {
        this.contribution = contribution;
    }

    public Group getGroup() {return group;}

    public void setGroup(Group group) {this.group = group;}

    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}

    public Double getAmountOfEarnings() {return amountOfEarnings;}

    public void setAmountOfEarnings(Double amountOfEarnings) {
        this.amountOfEarnings = amountOfEarnings;
    }
}
