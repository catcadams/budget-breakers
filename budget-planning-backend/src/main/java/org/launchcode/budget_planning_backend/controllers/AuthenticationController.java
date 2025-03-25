package org.launchcode.budget_planning_backend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
//import org.launchcode.budget_planning_backend.data.UserRepository;
import org.launchcode.budget_planning_backend.models.dto.LoginFormDTO;
import org.launchcode.budget_planning_backend.models.dto.RegisterFormDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.launchcode.budget_planning_backend.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class AuthenticationController {

    private final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

//    @Autowired
//    UserRepository userRepository;

    private static final String userSessionKey = "user";

    User user;

//    public User getUserFromSession(HttpSession session) {
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
//    private static void setUserInSession(HttpSession session, User user) {
//        session.setAttribute(userSessionKey, user.getId());
//    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> processRegistrationForm(@RequestBody @Valid RegisterFormDTO registerFormDTO,
                                          Errors errors, HttpServletRequest request) {



        Map<String, String> response = new HashMap<>();
        if (errors.hasErrors()) {
            System.out.println(errors.toString());
            response.put("message", "Registration errors occurred");
            return ResponseEntity.badRequest().body(response);
        }

        logger.info("New User: ".concat(registerFormDTO.getFirstName()));

//        User existingUser = userRepository.findByUsername(registerFormDTO.getUsername());
//
//        if (existingUser != null) {
//            errors.rejectValue("username", "username.alreadyexists", "A user with that username already exists");
//            model.addAttribute("title", "Register");
//            return "register";
//        }

        String password = registerFormDTO.getPassword();
        String verifyPassword = registerFormDTO.getVerifyPassword();

        if (!registerFormDTO.isMatchingPassword(verifyPassword)) {
            response.put("message", "Password confirmation error occurred");
            return ResponseEntity.badRequest().body(response);
        }
        System.out.println("Password matched?");

        List<User> users = new ArrayList<>();

        User newUser = new User(registerFormDTO.getFirstName(),
                registerFormDTO.getLastName(),
                registerFormDTO.getDateOfBirth(),
                registerFormDTO.getEmail(),
                registerFormDTO.getUsername(),
                registerFormDTO.getPassword(),
                registerFormDTO.getVerifyPassword());
        users.add(newUser);
//        setUserInSession(request.getSession(), newUser);
//
        response.put("message", "Registration successful");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> processLoginForm(@RequestBody @Valid LoginFormDTO loginFormDTO,
                                                                Errors errors, HttpServletRequest request) {

        logger.info("New login: ".concat(loginFormDTO.getUsername()));

        Map<String, String> response = new HashMap<>();
        if (errors.hasErrors()) {
            response.put("message", "Validation errors occurred");
            return ResponseEntity.badRequest().body(response);
        }

        String dummyUsername = "user123";
        String dummyPassword = "secure123";

            if (loginFormDTO.getUsername().equals(dummyUsername) && loginFormDTO.getPassword().equals(dummyPassword)) {
                response.put("message", "Login successful");
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "Invalid credentials");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        }

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
//        }
//
//        setUserInSession(request.getSession(), theUser);

        //return "redirect:";
    }

//    @GetMapping("/logout")
//    public String logout(HttpServletRequest request){
//        request.getSession().invalidate();
//        return "redirect:/login";
//    }

//}
