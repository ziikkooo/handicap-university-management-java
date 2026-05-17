package com.university.handicap.services;

import com.university.handicap.dao.UserDAO;
import com.university.handicap.models.User;

public class AuthService {

    private final UserDAO userDAO = new UserDAO();

    public User login(String email, String password) {
        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            return null;
        }
        User user = userDAO.findByEmailAndPassword(email, password);
        if (user != null) {
            SessionService.startSession(user);
        }
        return user;
    }

    public void logout() {
        SessionService.endSession();
    }

    public User getCurrentUser() {
        return SessionService.getCurrentUser();
    }

    public boolean isAdmin() {
        return SessionService.isAdmin();
    }

    public boolean isPersonneHandicap() {
        return SessionService.isPersonneHandicap();
    }
}
