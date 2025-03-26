package org.launchcode.budget_planning_backend.models;


import static org.launchcode.budget_planning_backend.models.DummyObjectsToBeDeleted.getGroupByName;

public class Chore extends AbstractEntity {

    private static int nextId = 0;

    private User user;

    private Event event;

    private Contributions contribution;

    private UserGroup userGroup;

    private Status status;

    private Double amountOfEarnings;

    public Chore(String name, String description, Double amountOfEarnings){
        setId(nextId++);
        setName(name);
        setDescription(description);
        this.amountOfEarnings = amountOfEarnings;
    }

    public static Chore createNewChore(ChoreDto choreDto) {
        Chore chore = new Chore(choreDto.getName(), choreDto.getDescription(), choreDto.getAmountOfEarnings());
        //temp userGroup handling, will be replaced after Groups controllers are implemented
        UserGroup group = getGroupByName(choreDto.getUserGroupName());
        chore.setStatus(Status.OPEN);
        chore.setGroup(group);
        return chore;
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

    public UserGroup getGroup() {
        return userGroup;
    }

    public void setGroup(UserGroup group) {
        this.userGroup = group;
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
                ", group=" + userGroup.getName() + '}';
    }
}
