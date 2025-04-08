package org.launchcode.budget_planning_backend.service;

import jakarta.servlet.http.HttpServletRequest;
import org.launchcode.budget_planning_backend.models.SessionKey;
import org.launchcode.budget_planning_backend.models.User;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    public User getCurrentUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute(SessionKey.USER.getValue());
    }
}
