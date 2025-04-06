package org.launchcode.budget_planning_backend.models;

import java.time.LocalDate;

public class ContributionDTO{

    private Double amountOfContribution;
    private LocalDate date;
    private Status status;
    private User user;

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

    @Override
    public String toString() {
        return "ContributionDTO{" +
                "amountOfContribution=" + amountOfContribution +
                ", date=" + date +
                ", status=" + status +
                ", user=" + user +
                '}';
    }
}
