package org.launchcode.budget_planning_backend.models;

import java.util.Date;

public class EventDTO {

    private String eventName;
    private double eventBudget;
    private String eventLocation;
    private String eventDescription;
    private String eventDate;
    private double eventEarnings;

    public EventDTO(){}

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public double getEventBudget() {
        return eventBudget;
    }

    public void setEventBudget(double eventBudget) {
        this.eventBudget = eventBudget;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public double getEventEarnings() {
        return eventEarnings;
    }

    public void setEventEarnings(double eventEarnings) {
        this.eventEarnings = eventEarnings;
    }
}
