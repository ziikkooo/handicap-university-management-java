package com.university.handicap.services;

import com.university.handicap.dao.StatistiqueDAO;
import com.university.handicap.models.DashboardStats;
import java.sql.Date;

public class DashboardService {

    private StatistiqueDAO statistiqueDAO;

    public DashboardService() {
        this.statistiqueDAO = new StatistiqueDAO();
    }

    public DashboardStats getAllStats() {
        DashboardStats stats = new DashboardStats();

        stats.setDemandesEnCours(statistiqueDAO.countDemandesByStatut("EN_COURS"));
        stats.setDemandesAcceptees(statistiqueDAO.countDemandesByStatut("ACCEPTEE"));
        stats.setDemandesRefusees(statistiqueDAO.countDemandesByStatut("REFUSEE"));

        // stats des reclamations
        stats.setReclamationsEnCours(statistiqueDAO.countReclamationsByStatut("EN_COURS"));
        stats.setReclamationsTraitees(statistiqueDAO.countReclamationsByStatut("TRAITEE"));
        stats.setReclamationsRejetees(statistiqueDAO.countReclamationsByStatut("REJETEE"));

        // stats par type de demande
        stats.setDemandesAmenagement(statistiqueDAO.countDemandesByType("AMENAGEMENT_EXAMEN"));
        stats.setDemandesAccessibilite(statistiqueDAO.countDemandesByType("ACCESSIBILITE"));
        stats.setDemandesAccompagnement(statistiqueDAO.countDemandesByType("ACCOMPAGNEMENT"));
        stats.setDemandesAutre(statistiqueDAO.countDemandesByType("AUTRE"));

        return stats;
    }

    // statut précis
    public int countDemandesByStatut(String statut) {
        return statistiqueDAO.countDemandesByStatut(statut);
    }

    //  un type precis
    public int countDemandesByType(String type) {
        return statistiqueDAO.countDemandesByType(type);
    }

    // statut de reclamation
    public int countReclamationsByStatut(String statut) {
        return statistiqueDAO.countReclamationsByStatut(statut);
    }

    // filtrage par date 
    public int getDemandesBetweenDates(Date dateDebut, Date dateFin) {
        return statistiqueDAO.countDemandesBetweenDates(dateDebut, dateFin);
    }

    // stats pour une annee donnee
    public DashboardStats getAnnualStats(int annee) {
        DashboardStats stats = new DashboardStats();
        stats.setAnnee(annee);
        stats.setTotalDemandesAnnee(statistiqueDAO.countDemandesByAnnee(annee));
        stats.setTotalReclamationsAnnee(statistiqueDAO.countReclamationsByAnnee(annee));
        return stats;
    }
}
