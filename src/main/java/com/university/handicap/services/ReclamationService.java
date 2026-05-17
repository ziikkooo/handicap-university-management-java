package com.university.handicap.services;

import com.university.handicap.dao.HistoriqueReclamationDAO;
import com.university.handicap.dao.ReclamationDAO;
import com.university.handicap.models.HistoriqueReclamation;
import com.university.handicap.models.Reclamation;
import com.university.handicap.models.StatutReclamation;

import java.util.List;

public class ReclamationService {

    private final ReclamationDAO reclamationDAO;
    private final HistoriqueReclamationDAO historiqueDAO;

    public ReclamationService() {
        this.reclamationDAO = new ReclamationDAO();
        this.historiqueDAO = new HistoriqueReclamationDAO();
    }

    public boolean createReclamation(Reclamation reclamation) {
        if (!isValidReclamation(reclamation)) {
            return false;
        }

        if (reclamation.getStatut() == null) {
            reclamation.setStatut(StatutReclamation.EN_COURS);
        }

        return reclamationDAO.createReclamation(reclamation);
    }

    public boolean updateReclamation(Reclamation reclamation) {
        if (reclamation == null || reclamation.getId() <= 0) {
            return false;
        }

        if (!isValidReclamation(reclamation)) {
            return false;
        }

        return reclamationDAO.updateReclamation(reclamation);
    }

    public boolean deleteReclamation(int id) {
        if (id <= 0) {
            return false;
        }

        return reclamationDAO.deleteReclamation(id);
    }

    public boolean changeStatut(int reclamationId, StatutReclamation nouveauStatut, int adminId) {
        if (reclamationId <= 0 || nouveauStatut == null) {
            return false;
        }

        Reclamation reclamation = reclamationDAO.getReclamationById(reclamationId);

        if (reclamation == null) {
            return false;
        }

        String ancienStatut = reclamation.getStatut().name();
        boolean changed = reclamationDAO.changeStatut(reclamationId, nouveauStatut);

        if (changed) {
            HistoriqueReclamation historique = new HistoriqueReclamation();
            historique.setReclamationId(reclamationId);
            historique.setAdminId(adminId);
            historique.setAncienStatut(ancienStatut);
            historique.setNouveauStatut(nouveauStatut.name());
            historique.setAction("Changement statut reclamation");

            historiqueDAO.addHistorique(historique);
        }

        return changed;
    }

    public Reclamation getReclamationById(int id) {
        if (id <= 0) {
            return null;
        }

        return reclamationDAO.getReclamationById(id);
    }

    public List<Reclamation> getAllReclamations() {
        return reclamationDAO.getAllReclamations();
    }

    public List<Reclamation> getReclamationsByUser(int userId) {
        if (userId <= 0) {
            return List.of();
        }

        return reclamationDAO.getReclamationsByUser(userId);
    }

    public List<Reclamation> getReclamationsByStatut(StatutReclamation statut) {
        if (statut == null) {
            return List.of();
        }

        return reclamationDAO.getReclamationsByStatut(statut);
    }

    public List<HistoriqueReclamation> getHistoriqueReclamation(int reclamationId) {
        if (reclamationId <= 0) {
            return List.of();
        }

        return historiqueDAO.getHistoriqueByReclamation(reclamationId);
    }

    private boolean isValidReclamation(Reclamation reclamation) {
        if (reclamation == null) {
            return false;
        }

        if (reclamation.getUserId() <= 0) {
            return false;
        }

        if (isEmpty(reclamation.getSujet())) {
            return false;
        }

        return !isEmpty(reclamation.getDescription());
    }

    private boolean isEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }
}
