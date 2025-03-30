package org.launchcode.budget_planning_backend.models.dto;

import org.launchcode.budget_planning_backend.models.*;

public class UserGroupDTO extends AbstractEntity {

    private User user;

    private Event event;

    private ChoreDto choreDto;

    public UserGroupDTO() {}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public ChoreDto getChoreDto() {
        return choreDto;
    }

    public void setChoreDto(ChoreDto choreDto) {
        this.choreDto = choreDto;
    }
}
