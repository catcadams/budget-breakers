package org.launchcode.budget_planning_backend.models.dto;
import org.launchcode.budget_planning_backend.models.User;
import java.util.Date;

public class RegisterFormDTO extends User {

    public RegisterFormDTO(String firstName, String lastName, Date dateOfBirth, String username, String password, String verifyPassword, String email) {
        super(firstName, lastName, dateOfBirth, username, password, verifyPassword, email);
    }
}
