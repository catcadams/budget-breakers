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
//    @NotBlank(message = "A group selection is required.")
    private UserGroup userGroup;

    private Status status;

//    @NotBlank(message = "Amount of earnings for this chore is required.")
    private Double amountOfEarnings;

    public Chore(String name, String description, Double amountOfEarnings) {
        setName(name);
        setDescription(description);
        this.amountOfEarnings = amountOfEarnings;
    }

    public Chore () {}

    public Double getAmountOfEarnings() {
        return amountOfEarnings;
    }

    public void setAmountOfEarnings(Double amountOfEarnings) {
        this.amountOfEarnings = amountOfEarnings;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public UserGroup getGroup() {
        return userGroup;
    }

    public void setGroup(UserGroup group) {
        this.userGroup = group;
    }


    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }

    public Contributions getContribution() {
        return contribution;
    }

    public void setContribution(Contributions contribution) {
        this.contribution = contribution;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "Chore{" + "id=" + getId() + ", " +
                "name=" + getName() +
                ", description=" + getDescription() +
                ", amountOfEarnings=" + getAmountOfEarnings() +
                ", status=" + getStatus() +
                ", user=" + user +
                ", event=" + event +
                ", contribution=" + contribution +
                ", group=" + userGroup + '}';
    }
}
