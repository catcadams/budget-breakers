package org.launchcode.budget_planning_backend.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    List<User> users = new ArrayList<>();
    Map<String, String> response = new HashMap<>();
    private final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private User findByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
//    @Autowired
//    UserRepository userRepository;


    public User getUserFromSession(HttpSession session) {
        return (User) session.getAttribute(SessionKey.USER.getValue());
    }

    //For persistence with database connection
//        Integer userId = (Integer) session.getAttribute(userSessionKey);
//        if (userId == null) {
//            return null;
//        }
//
////        Optional<User> user = userRepository.findById(userId);
////
////        if (user.isEmpty()) {
////            return null;
////        }
////
////        return user.get();
//
//    }
//
    private static void setUserInSession(HttpSession session, User user) {
//        session.setAttribute(userSessionKey, user.getId());
        session.setAttribute(SessionKey.USER.getValue(), user);
    }

    @GetMapping()
    public ResponseEntity<User> getCurrentUser(HttpServletRequest request){
        User currentUser = authenticationService.getCurrentUser(request);
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
//        User existingUser = userRepository.findByUsername(registerFormDTO.getUsername());
//
//        if (existingUser != null) {
//            errors.rejectValue("username", "username.alreadyexists", "A user with that username already exists");
//            model.addAttribute("title", "Register");
//            return "register";
//        }

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
                registerFormDTO.getUsername(),
                registerFormDTO.getPassword(),
                registerFormDTO.getVerifyPassword());

        AccountTypeUtil.determineAccountType(newUser);

        //userRepository.save(user);
        users.add(newUser);
        //setUserInSession(request.getSession(), newUser);
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
        logger.info("Session ID Register: " + session.getId());

        User user = findByUsername(username);
        if (user != null && loginFormDTO.getPassword().equals(user.getPassword())) {
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

    //For persistence with database connection
//        User theUser = userRepository.findByUsername(loginFormDTO.getUsername());
//
//        if (theUser == null) {
//            errors.rejectValue("username", "user.invalid", "The given username does not exist");
//            model.addAttribute("title", "Log In");
//            return "login";
//        }

//        String password = loginFormDTO.getPassword();
//
//        if (!theUser.isMatchingPassword(password)) {
//            errors.rejectValue("password", "password.invalid", "Invalid password");
//            model.addAttribute("title", "Log In");
//            return "login";
//
//        setUserInSession(request.getSession(), theUser);

    //return "redirect:";
//    }

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
//}
