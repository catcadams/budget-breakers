package org.launchcode.budget_planning_backend.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ChoreDto extends AbstractEntity {

    private UserGroup group;

    private String userGroupName;//temp for dummy data

//    @Positive(message = "Amount must be a positive number.")
//    @NotBlank(message = "Amount is required.")
    private Double amountOfEarnings;

    public ChoreDto() {}

    //public User getUser() {return user;}

    //public void setUser(User user) {this.user = user;}

    //public Event getEvent() {return event;}

    //public void setEvent(Event event) {this.event = event;}

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
}
