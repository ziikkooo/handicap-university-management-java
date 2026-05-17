package com.university.handicap.services;

import com.university.handicap.dao.DemandeDAO;
import com.university.handicap.models.Demande;
import com.university.handicap.models.TypeDemande;
import com.university.handicap.models.StatutDemande;

import java.util.List;

public class DemandeService {

    private final DemandeDAO demandeDAO;

    public DemandeService() {
        this.demandeDAO = new DemandeDAO();
    }

    // ── CREATE ────────────────────────────────────────────────

    public boolean createDemande(int userId, TypeDemande type, String description) {
        if (type == null) {
            System.err.println("Service: type cannot be null.");
            return false;
        }
        if (description == null || description.trim().isEmpty()) {
            System.err.println("Service: description cannot be empty.");
            return false;
        }
        Demande demande = new Demande(userId, type, description.trim());
        return demandeDAO.create(demande);
    }

    // ── UPDATE ────────────────────────────────────────────────

    public boolean updateDemande(int id, TypeDemande type, StatutDemande statut, String description) {
        if (description == null || description.trim().isEmpty()) {
            System.err.println("Service: description cannot be empty.");
            return false;
        }
        Demande demande = new Demande();
        demande.setId         (id);
        demande.setType       (type);
        demande.setStatut     (statut);
        demande.setDescription(description.trim());
        return demandeDAO.update(demande);
    }

    // ── DELETE ────────────────────────────────────────────────

    public boolean deleteDemande(int id) {
        if (id <= 0) {
            System.err.println("Service: invalid id.");
            return false;
        }
        return demandeDAO.delete(id);
    }

    // ── READ ──────────────────────────────────────────────────

    public List<Demande> getAllDemandes() {
        return demandeDAO.findAll();
    }

    public List<Demande> getDemandesByUser(int userId) {
        return demandeDAO.findByUser(userId);
    }

    public List<Demande> getDemandesByType(TypeDemande type) {
        return demandeDAO.findByType(type);
    }
}
