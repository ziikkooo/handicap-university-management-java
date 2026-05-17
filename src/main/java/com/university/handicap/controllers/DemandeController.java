package com.university.handicap.controllers;

import com.university.handicap.services.DemandeService;
import com.university.handicap.models.Demande;
import com.university.handicap.models.TypeDemande;
import com.university.handicap.models.StatutDemande;

import java.util.ArrayList;
import java.util.List;

public class DemandeController {

    private final DemandeService demandeService;

    public DemandeController() {
        this.demandeService = new DemandeService();
    }

    // ── CREATE ────────────────────────────────────────────────
    // Called from your Swing "Ajouter" button

    public boolean createDemande(int userId, String type, String description) {
        try {
            TypeDemande typeDemande = TypeDemande.valueOf(type);
            return demandeService.createDemande(userId, typeDemande, description);
        } catch (IllegalArgumentException e) {
            System.err.println("Controller: unknown type '" + type + "'");
            return false;
        }
    }

    // ── UPDATE ────────────────────────────────────────────────
    // Called from your Swing "Modifier" button

    public boolean updateDemande(int id, String type, String statut, String description) {
        try {
            TypeDemande   typeDemande   = TypeDemande  .valueOf(type);
            StatutDemande statutDemande = StatutDemande.valueOf(statut);
            return demandeService.updateDemande(id, typeDemande, statutDemande, description);
        } catch (IllegalArgumentException e) {
            System.err.println("Controller: invalid type or statut value.");
            return false;
        }
    }

    // ── DELETE ────────────────────────────────────────────────
    // Called from your Swing "Supprimer" button

    public boolean deleteDemande(int id) {
        return demandeService.deleteDemande(id);
    }

    // ── DISPLAY ALL ───────────────────────────────────────────
    // Used to populate a JTable with all demandes

    public List<Demande> getAllDemandes() {
        return demandeService.getAllDemandes();
    }

    // ── DISPLAY BY USER ───────────────────────────────────────
    // Used to show only the logged-in user's demandes

    public List<Demande> getDemandesByUser(int userId) {
        return demandeService.getDemandesByUser(userId);
    }

    // ── FILTER BY TYPE ────────────────────────────────────────
    // Called from your Swing JComboBox filter

    public List<Demande> filterByType(String type) {
        try {
            TypeDemande typeDemande = TypeDemande.valueOf(type);
            return demandeService.getDemandesByType(typeDemande);
        } catch (IllegalArgumentException e) {
            System.err.println("Controller: unknown type '" + type + "'");
            return new ArrayList<>();
        }
    }
}
