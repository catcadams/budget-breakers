package org.launchcode.budget_planning_backend.models;

public enum Status {
    OPEN("open"),
    IN_PROGRESS("inProgress"),
    COMPLETE("complete"),
    PENDING("pending"),
    CANCELLED("cancelled");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
