package com.university.handicap.controllers;

import com.university.handicap.models.ArchiveRecord;
import com.university.handicap.models.SearchCriteria;
import com.university.handicap.services.ArchiveService;
import com.university.handicap.services.SearchService;

import java.sql.ResultSet;
import java.util.List;

public class ArchiveController {

    private final ArchiveService archiveService = new ArchiveService();
    private final SearchService  searchService  = new SearchService();

    public boolean archiveClosedDemande(int demandeId, String description, int adminId) {
        if (demandeId <= 0) throw new IllegalArgumentException("ID demande invalide.");
        return archiveService.archiveClosedDemande(demandeId, description, adminId);
    }

    public boolean archiveClosedReclamation(int reclamationId, String description, int adminId) {
        if (reclamationId <= 0) throw new IllegalArgumentException("ID reclamation invalide.");
        return archiveService.archiveClosedReclamation(reclamationId, description, adminId);
    }

    public List<ArchiveRecord> getAllArchives() {
        return archiveService.getAllArchives();
    }

    public List<ArchiveRecord> getDemandeArchives() {
        return archiveService.getDemandeArchives();
    }

    public List<ArchiveRecord> getReclamationArchives() {
        return archiveService.getReclamationArchives();
    }

    public List<ResultSet> searchDemandes(SearchCriteria criteria) {
        return searchService.searchDemandes(criteria);
    }

    public List<ResultSet> searchReclamations(SearchCriteria criteria) {
        return searchService.searchReclamations(criteria);
    }

    public List<ResultSet> getCompleteHistoryByUser(int userId) {
        if (userId <= 0) throw new IllegalArgumentException("ID utilisateur invalide.");
        return searchService.getCompleteHistoryByUser(userId);
    }
}
