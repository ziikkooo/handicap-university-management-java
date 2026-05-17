package com.university.handicap.controllers;

import com.university.handicap.models.User;
import com.university.handicap.services.AuthService;
import com.university.handicap.services.SessionService;

public class AuthController {

    private final AuthService authService = new AuthService();

    public User login(String email, String password) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email ne peut pas etre vide.");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Mot de passe ne peut pas etre vide.");
        }
        User user = authService.login(email, password);
        if (user == null) {
            throw new IllegalArgumentException("Identifiants incorrects.");
        }
        return user;
    }

    public void logout() {
        authService.logout();
    }

    public User getCurrentUser() {
        return authService.getCurrentUser();
    }

    public boolean isAdmin() {
        return authService.isAdmin();
    }

    public boolean isPersonneHandicap() {
        return authService.isPersonneHandicap();
    }

    public boolean isLoggedIn() {
        return SessionService.isLoggedIn();
    }
}
