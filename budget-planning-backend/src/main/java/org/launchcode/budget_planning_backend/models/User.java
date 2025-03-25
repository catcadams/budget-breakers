package org.launchcode.budget_planning_backend.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User extends BaseAbstractEntity{

    private static int nextId = 1;

    private final List<Group> groups = new ArrayList<>();

    @NotBlank(message = "Firstname is required")
    public String firstName;

    @NotBlank(message = "Lastname is required")
    private String lastName;

    @NotNull(message = "Date of Birth is required")
    private Date dateOfBirth;

    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 15, message = "Username must be between 4 and 15 characters")
    private String username;

    @NotBlank(message = "Password is required")
    protected String pwHash;

    @NotBlank(message = "Password is required")
    private String verifyPassword;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Email.Try Again")
    private String email;

    protected static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private AccountType accountType;

    public User(String firstName, String lastName, Date dateOfBirth, String username, String password, String verifyPassword, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.username = username;
        this.pwHash = encoder.encode(password);
        this.verifyPassword = verifyPassword;
        this.email = email;
        this.setId(nextId);
        nextId++;
    }

//    public static User addUser(String firstName, String lastName, Date dateOfBirth, String email, String username, String pwHash) {
//        return new User(firstName, lastName, dateOfBirth, email, username, pwHash);
//    }

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
        return pwHash;
    }

    public void setPassword(String pwHash) {
        this.pwHash = pwHash;
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

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }
    public List<Group> getGroups() {
        return groups;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
}
