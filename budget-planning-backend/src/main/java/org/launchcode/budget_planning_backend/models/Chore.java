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
    private UserGroup userGroup;

    private Status status;

    @NotBlank(message = "Amount of earnings for this chore is required.")
    private Double amountOfEarnings;

    public Chore(String name, String description, Double amountOfEarnings) {
        setName(name);
        setDescription(description);
        this.amountOfEarnings = amountOfEarnings;
    }

    public static Chore createChore(String name, String description, Double amountOfEarnings) {
        return new Chore(name, description, amountOfEarnings);
    }

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
