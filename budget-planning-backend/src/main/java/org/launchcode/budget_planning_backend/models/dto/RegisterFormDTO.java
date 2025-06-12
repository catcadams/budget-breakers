package org.launchcode.budget_planning_backend.models.dto;
import org.launchcode.budget_planning_backend.models.User;
import java.time.LocalDate;

public class RegisterFormDTO extends User {

    private String password;

    private String verifyPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }
    public RegisterFormDTO(String firstName, String lastName, LocalDate dateOfBirth, String email, String username) {
        super(firstName, lastName, dateOfBirth, email, username);
    }
}
