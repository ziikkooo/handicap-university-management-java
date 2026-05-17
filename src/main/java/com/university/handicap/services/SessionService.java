package com.university.handicap.services;

import com.university.handicap.models.Role;
import com.university.handicap.models.User;

public class SessionService {

    private static User currentUser = null;

    public static void startSession(User user) {
        currentUser = user;
    }

    public static void endSession() {
        currentUser = null;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    public static boolean isAdmin() {
        return currentUser != null && currentUser.getRole() == Role.ADMIN;
    }

    public static boolean isPersonneHandicap() {
        return currentUser != null && currentUser.getRole() == Role.PERSONNE_HANDICAP;
    }
}
