package org.launchcode.budget_planning_backend.models;

import java.time.LocalDate;

public class ContributionDTO{

    private int id;
    private Double amountOfContribution;
    private LocalDate date;
    private Status status;
    private User user;
    private String name;
    public Double getAmountOfContribution() {
        return amountOfContribution;
    }

    public void setAmountOfContribution(Double amountOfContribution) {
        this.amountOfContribution = amountOfContribution;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ContributionDTO{" +
                "ID"+id+
                "amountOfContribution=" + amountOfContribution +
                ", date=" + date +
                ", status=" + status +
                ", user=" + user +
                '}';
    }
}
