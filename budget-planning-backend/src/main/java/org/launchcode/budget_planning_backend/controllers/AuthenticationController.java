package org.launchcode.budget_planning_backend.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.launchcode.budget_planning_backend.data.UserRepository;
import org.launchcode.budget_planning_backend.models.AccountTypeUtil;
import org.launchcode.budget_planning_backend.models.SessionKey;
import org.launchcode.budget_planning_backend.models.User;
import org.launchcode.budget_planning_backend.models.dto.LoginFormDTO;
import org.launchcode.budget_planning_backend.models.dto.RegisterFormDTO;
import org.launchcode.budget_planning_backend.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class AuthenticationController {

    Map<String, String> response = new HashMap<>();
    private final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    UserRepository userRepository;

    private static final String userSessionKey = "user";

    public User getUserFromSession(HttpSession session) {
    //For persistence with database connection
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        if (userId == null) {
            return null;
        }
        Optional<User> user = userRepository.findById(userId);
        return user.orElse(null);
    }

    private static void setUserInSession(HttpSession session, User user) {
        session.setAttribute(userSessionKey, user.getId());
    }

    @GetMapping()
    public ResponseEntity<User> getCurrentUser(HttpServletRequest request){
        User currentUser = getUserFromSession(request.getSession());
        if(currentUser != null) {
            return ResponseEntity.ok(currentUser);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> processRegistrationForm(@RequestBody @Valid RegisterFormDTO registerFormDTO,
                                                                       Errors errors, HttpServletRequest request) {

        if (errors.hasErrors()) {
            logger.info("Errors:" + errors);
            response.put("message", "Registration errors occurred");
            return ResponseEntity.badRequest().body(response);
        }

        //For persistence with database connection
        User existingUser = userRepository.findByUsername(registerFormDTO.getUsername());

        if (existingUser != null) {
            logger.info("Username already exists:" + existingUser.getUsername());
            response.put("message", "A user with that username already exists");
            return ResponseEntity.badRequest().body(response);
        }

        String password = registerFormDTO.getPassword();
        String verifyPassword = registerFormDTO.getVerifyPassword();

        if (!password.equals(verifyPassword)) {
            response.put("message", "Password confirmation error occurred");
            return ResponseEntity.badRequest().body(response);
        }

        User newUser = new User(registerFormDTO.getFirstName(),
                registerFormDTO.getLastName(),
                registerFormDTO.getDateOfBirth(),
                registerFormDTO.getEmail(),
                registerFormDTO.getUsername());

        newUser.setPassword(registerFormDTO.getPassword());

        AccountTypeUtil.determineAccountType(newUser);

        userRepository.save(newUser);
        logger.info("User stored in session: " + newUser.getUsername());
        logger.info("User acct type: " + newUser.getAccountType());

        response.put("message", "Registration successful");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> processLoginForm(@RequestBody @Valid LoginFormDTO loginFormDTO,
                                                                Errors errors, HttpServletRequest request) {

        String username = loginFormDTO.getUsername().trim();
        logger.info("Session ID Login".concat(request.getSession().getId()));

        HttpSession session = request.getSession(true); // Creates session if it doesn't exist already

        User user = userRepository.findByUsername(username);
        System.out.println(user);
        if(user == null) {
            logger.info("Username does not exist: " + user);
            response.put("message", "Username does not exist. Please try again");
            return ResponseEntity.badRequest().body(response);
        }
        if (user != null && user.isMatchingPassword(loginFormDTO.getPassword())) {
            if (session!= null) {
                session.removeAttribute("user");
            }
            session.setAttribute("user", user);

            if (session == null) {
                session = request.getSession(true); // Create new session if invalid
            }

            setUserInSession(session, user);
            response.put("message", "Login successful");
            return ResponseEntity.ok(response);
        }
        response.put("message", "Invalid credentials");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

//logout functionality
    @GetMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpServletRequest request, HttpServletResponse respond){
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute("user");
            session.invalidate();
        }

        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setMaxAge(0); // Expire cookie
        cookie.setPath("/"); // Clear cookie
        cookie.setSecure(true);
        respond.addCookie(cookie);

        response.put("message", "Logged out successfully");
        return ResponseEntity.ok(response);
    }
}
