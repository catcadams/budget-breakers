package org.launchcode.budget_planning_backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Event extends AbstractEntity{

    @NotBlank(message = "Budget amount is required")
    private double budget;

    @NotBlank(message = "Location is required")
    @Size(min = 4, max = 50 , message = "Location must be  between 4 and 50 characters")
    private String location;

    private Date date;

    private Status status;

    private double earnings;

    @ManyToOne
    @NotNull(message = "Group is required")
    private Group group;

    @OneToMany
    private final List<Contributions> contributions = new ArrayList<>();

    public Event(double budget, String location, Date date, Status status, double earnings, Group group) {
        this.budget = budget;
        this.location = location;
        this.date = date;
        this.status = status;
        this.earnings = earnings;
        this.group = group;
    }

    public Event(){}

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public double getEarnings() {
        return earnings;
    }

    public void setEarnings(double earnings) {
        this.earnings = earnings;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<Contributions> getContributions() {
        return contributions;
    }

    public void addContribution(Contributions contribution) {
        this.contributions.add(contribution);
    }
}
