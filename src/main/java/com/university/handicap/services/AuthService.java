package com.university.handicap.services;

import com.university.handicap.dao.UserDAO;
import com.university.handicap.dao.PersonneHandicapDAO;
import com.university.handicap.models.User;
import com.university.handicap.models.Role;
import com.university.handicap.models.PersonneHandicap;

public class AuthService {

    private final UserDAO userDAO = new UserDAO();
    private final PersonneHandicapDAO personneDAO = new PersonneHandicapDAO();

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


    public boolean registerPersonne(String nom, String prenom, String email, String password,
                                    String telephone, String typeHandicap, String filiere, String niveau) {

        if (isBlank(nom) || isBlank(prenom) || isBlank(email) || isBlank(password)) {
            return false;
        }

        if (userDAO.findByEmail(email) != null) {
            return false;
        }

        User user = new User();
        user.setNom(nom);
        user.setPrenom(prenom);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(Role.PERSONNE_HANDICAP);

        int userId = userDAO.createUser(user);

        if (userId <= 0) {
            return false;
        }

        PersonneHandicap personne = new PersonneHandicap();
        personne.setUserId(userId);
        personne.setTelephone(telephone);
        personne.setTypeHandicap(typeHandicap);
        personne.setFiliere(filiere);
        personne.setNiveau(niveau);

        return personneDAO.addPersonne(personne);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
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
