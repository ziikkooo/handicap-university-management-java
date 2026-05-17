package com.university.handicap.controllers;

import com.university.handicap.models.PersonneHandicap;
import com.university.handicap.services.UserManagementService;

import java.util.List;

public class UserController {

    private final UserManagementService userService;

    public UserController() {
        this.userService = new UserManagementService();
    }

    public boolean addPersonne(PersonneHandicap personne) {
        return userService.addPersonne(personne);
    }

    public boolean updatePersonne(PersonneHandicap personne) {
        return userService.updatePersonne(personne);
    }

    public boolean deletePersonne(int id) {
        return userService.deletePersonne(id);
    }

    public PersonneHandicap getPersonneById(int id) {
        return userService.getPersonneById(id);
    }

    public PersonneHandicap getPersonneByUserId(int userId) {
        return userService.getPersonneByUserId(userId);
    }

    public List<PersonneHandicap> getAllPersonnes() {
        return userService.getAllPersonnes();
    }

    public List<PersonneHandicap> searchPersonnes(String keyword) {
        return userService.searchPersonnes(keyword);
    }
}
