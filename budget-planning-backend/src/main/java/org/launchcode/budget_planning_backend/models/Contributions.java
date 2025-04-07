package org.launchcode.budget_planning_backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Date;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Contributions extends BaseAbstractEntity{

    @NotNull
    private User user;

    @NotNull
    private Double amountOfContribution;

    @NotNull
    private LocalDate date;

    @NotNull
    @JsonBackReference
    private Event event;

    private int eventID;

    @NotNull
    private Status status;

    private static int nextId = 1;

    public Contributions() {
        this.setId(nextId);
        nextId++;
    }

    public User getUser() {return user;}

    public void setUser(User user) {this.user = user;}

    public Double getAmountOfContribution() {return amountOfContribution;}

    public void setAmountOfContribution(Double amountOfContribution) {
        this.amountOfContribution = amountOfContribution;
    }

    public LocalDate getDate() {return date;}

    public void setDate(LocalDate date) {this.date = date;}

    public Event getEvent() {return event;}

    public void setEvent(Event event) {this.event = event;}

    public Status getStatus() {return status;}

    public void setStatus(Status status) {this.status = status;}

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    @Override
    public String toString() {
        return "Contributions{" +
                "id=" + getId() +
                " user=" + user +
                ", amountOfContribution=" + amountOfContribution +
                ", date=" + date +
                ", event=" + event +
                ", status=" + status +
                '}';
    }
}
