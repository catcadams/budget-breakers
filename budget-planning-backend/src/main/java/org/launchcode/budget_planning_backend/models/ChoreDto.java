package org.launchcode.budget_planning_backend.models;

public class ChoreDto extends AbstractEntity {

    private UserGroup group;

    private String userGroupName;

    private Double amountOfEarnings;

    private Status status;

    private User user;

    private Event event;

    public ChoreDto() {}

    public User getUser() {return user;}

    public void setUser(User user) {this.user = user;}

    public Event getEvent() {return event;}

    public void setEvent(Event event) {this.event = event;}

    public UserGroup getUserGroup() {
        return group;
    }

    public void setUserGroup(UserGroup group) {
        this.group = group;
    }

    public Double getAmountOfEarnings() {return amountOfEarnings;}

    public void setAmountOfEarnings(Double amountOfEarnings) {
        this.amountOfEarnings = amountOfEarnings;
    }

    public String getUserGroupName() {
        return userGroupName;
    }

    public void setUserGroupName(String userGroupName) {
        this.userGroupName = userGroupName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
