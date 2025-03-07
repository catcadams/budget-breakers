package org.launchcode.budget_planning_backend.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AbstractEntity extends BaseAbstractEntity{

    @NotBlank
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;

    @Size(min = 0 , max = 250, message = "Description must be less than 250 characters")
    private String description;

    public @NotBlank @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters") String getName() {
        return name;
    }

    public void setName(@NotBlank @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters") String name) {
        this.name = name;
    }

    public @Size(min = 0, max = 250, message = "Description must be less than 250 characters") String getDescription() {
        return description;
    }

    public void setDescription(@Size(min = 0, max = 250, message = "Description must be less than 250 characters") String description) {
        this.description = description;
    }

}
