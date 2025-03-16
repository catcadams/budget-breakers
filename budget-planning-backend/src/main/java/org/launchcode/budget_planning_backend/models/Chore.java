package org.launchcode.budget_planning_backend.models;


public class Chore extends AbstractEntity {

    private User user;

    private Event event;

    private Contributions contribution;

    private Group group;

    private String status;

    private Double amountOfEarnings;

    public Chore(String name, String description, Double amountOfEarnings){
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
}
