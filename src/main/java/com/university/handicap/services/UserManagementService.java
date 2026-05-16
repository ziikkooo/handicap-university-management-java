package com.university.handicap.services;

import com.university.handicap.dao.PersonneHandicapDAO;
import com.university.handicap.models.PersonneHandicap;

import java.util.List;

public class UserManagementService {

    private final PersonneHandicapDAO personneDAO;

    public UserManagementService() {
        this.personneDAO = new PersonneHandicapDAO();
    }

    public boolean addPersonne(PersonneHandicap personne) {
        if (!isValidPersonne(personne)) {
            return false;
        }

        return personneDAO.addPersonne(personne);
    }

    public boolean updatePersonne(PersonneHandicap personne) {
        if (personne == null || personne.getId() <= 0) {
            return false;
        }

        if (!isValidPersonne(personne)) {
            return false;
        }

        return personneDAO.updatePersonne(personne);
    }

    public boolean deletePersonne(int id) {
        if (id <= 0) {
            return false;
        }

        return personneDAO.deletePersonne(id);
    }

    public PersonneHandicap getPersonneById(int id) {
        if (id <= 0) {
            return null;
        }

        return personneDAO.getPersonneById(id);
    }

    public PersonneHandicap getPersonneByUserId(int userId) {
        if (userId <= 0) {
            return null;
        }

        return personneDAO.getPersonneByUserId(userId);
    }

    public List<PersonneHandicap> getAllPersonnes() {
        return personneDAO.getAllPersonnes();
    }

    public List<PersonneHandicap> searchPersonnes(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllPersonnes();
        }

        return personneDAO.searchPersonnes(keyword.trim());
    }

    private boolean isValidPersonne(PersonneHandicap personne) {
        if (personne == null) {
            return false;
        }

        if (personne.getUserId() <= 0) {
            return false;
        }

        if (isEmpty(personne.getNom()) || isEmpty(personne.getPrenom()) || isEmpty(personne.getEmail())) {
            return false;
        }

        return personne.getEmail().contains("@");
    }

    private boolean isEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }
}
