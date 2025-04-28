package org.launchcode.budget_planning_backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User extends BaseAbstractEntity{

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_user_groups", joinColumns = {@JoinColumn(name = "user_id")},
    inverseJoinColumns = {@JoinColumn(name = "user_group_id")})
    private final List<UserGroup> userGroups = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private final List<Chore> chores = new ArrayList<>();

    @NotBlank(message = "Firstname is required")
    private String firstName;

    @NotBlank(message = "Lastname is required")
    private String lastName;

    @NotNull(message = "Date of Birth is required")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 15, message = "Username must be between 4 and 15 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 4, max = 15, message = "Username must be between 4 and 15 characters")
    protected String password;

    @NotBlank(message = "Password is required")
    private String verifyPassword;

    private String email;

    private AccountType accountType;

    public User(String firstName, String lastName, LocalDate dateOfBirth, String email, String username, String password, String verifyPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.username = username;
        this.password = password;
        this.verifyPassword = verifyPassword;
    }

    public User(){}

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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
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

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<UserGroup> getUserGroups() {
        return userGroups;
    }

    public void addUserGroup(UserGroup group) {
        this.userGroups.add(group);
    }
    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public List<Chore> getChores() {
        return chores;
    }

    public void addChoreToUser(Chore chore) {
        this.chores.add(chore);
    }

    public void removeChoreFromUser(Chore chore) {
        this.chores.remove(chore);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + getId() + '\'' +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", verifyPassword='" + verifyPassword + '\'' +
                ", email='" + email + '\'' +
                ", accountType=" + accountType +
                '}';
    }
}

