package org.launchcode.budget_planning_backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class User extends BaseAbstractEntity{

        private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        @ManyToMany
        private final List<Group> groups = new ArrayList<>();

        @NotBlank(message = "Firstname is required")
        private String firstName;

        @NotBlank(message = "Lastname is required")
        private String lastName;

        @NotBlank(message = "Date of Birth is required")
        private Date dateOfBirth;

        @NotBlank(message = "Username is required")
        @Size(min = 4, max = 15, message = "Username must be between 4 and 15 characters")
        private String username;

        @NotNull
        @NotBlank(message = "Password is required")
        private String password;

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid Email.Try Again")
        private String email;

    public User(String firstName, String lastName, Date dateOfBirth, String username, String password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.username = username;
        this.password = encoder.encode(password);
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public @NotNull @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotNull @Email String email) {
        this.email = email;
    }

    public boolean isMatchingPassword(String password) {
        String candidateHash = encoder.encode(password);
        return candidateHash.equals(this.password);
    }
}
