package com.university.handicap.controllers;

import com.university.handicap.models.HistoriqueReclamation;
import com.university.handicap.models.Reclamation;
import com.university.handicap.models.StatutReclamation;
import com.university.handicap.services.ReclamationService;

import java.util.List;

public class ReclamationController {

    private final ReclamationService reclamationService;

    public ReclamationController() {
        this.reclamationService = new ReclamationService();
    }

    public boolean createReclamation(Reclamation reclamation) {
        return reclamationService.createReclamation(reclamation);
    }

    public boolean updateReclamation(Reclamation reclamation) {
        return reclamationService.updateReclamation(reclamation);
    }

    public boolean deleteReclamation(int id) {
        return reclamationService.deleteReclamation(id);
    }

    public boolean changeStatut(int reclamationId, StatutReclamation nouveauStatut, int adminId) {
        return reclamationService.changeStatut(reclamationId, nouveauStatut, adminId);
    }

    public Reclamation getReclamationById(int id) {
        return reclamationService.getReclamationById(id);
    }

    public List<Reclamation> getAllReclamations() {
        return reclamationService.getAllReclamations();
    }

    public List<Reclamation> getReclamationsByUser(int userId) {
        return reclamationService.getReclamationsByUser(userId);
    }

    public List<Reclamation> getReclamationsByStatut(StatutReclamation statut) {
        return reclamationService.getReclamationsByStatut(statut);
    }

    public List<HistoriqueReclamation> getHistoriqueReclamation(int reclamationId) {
        return reclamationService.getHistoriqueReclamation(reclamationId);
    }
}
