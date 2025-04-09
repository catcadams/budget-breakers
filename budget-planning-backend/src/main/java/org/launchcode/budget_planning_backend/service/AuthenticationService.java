package org.launchcode.budget_planning_backend.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.launchcode.budget_planning_backend.models.SessionKey;
import org.launchcode.budget_planning_backend.models.User;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    public User getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                return user;  // Return the user object if it's in the session
            }
            //return (User) request.getSession().getAttribute(SessionKey.USER.getValue());
        }
        return null;
    }
}

