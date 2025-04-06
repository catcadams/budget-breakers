package org.launchcode.budget_planning_backend.models;

public enum SessionKey {

    USER("user");

    private final String value;

    SessionKey(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
