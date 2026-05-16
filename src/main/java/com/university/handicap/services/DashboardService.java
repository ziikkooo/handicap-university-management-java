package com.university.handicap.services;

import com.university.handicap.dao.StatistiqueDAO;
import com.university.handicap.models.DashboardStats;
import java.sql.Date;

// le service fait le lien entre le controller et le DAO
// ici y'a pas trop de logique metier, on appelle juste le DAO
public class DashboardService {

    private StatistiqueDAO statistiqueDAO;

    public DashboardService() {
        this.statistiqueDAO = new StatistiqueDAO();
    }

    // methode principale : recupere toutes les stats d'un coup
    // c'est celle qu'on va utiliser pour afficher le dashboard complet
    public DashboardStats getAllStats() {
        DashboardStats stats = new DashboardStats();

        // remplir les stats des demandes
        stats.setDemandesEnCours(statistiqueDAO.countDemandesByStatut("EN_COURS"));
        stats.setDemandesAcceptees(statistiqueDAO.countDemandesByStatut("ACCEPTEE"));
        stats.setDemandesRefusees(statistiqueDAO.countDemandesByStatut("REFUSEE"));

        // remplir les stats des reclamations
        stats.setReclamationsEnCours(statistiqueDAO.countReclamationsByStatut("EN_COURS"));
        stats.setReclamationsTraitees(statistiqueDAO.countReclamationsByStatut("TRAITEE"));
        stats.setReclamationsRejetees(statistiqueDAO.countReclamationsByStatut("REJETEE"));

        // remplir les stats par type de demande
        stats.setDemandesAmenagement(statistiqueDAO.countDemandesByType("AMENAGEMENT_EXAMEN"));
        stats.setDemandesAccessibilite(statistiqueDAO.countDemandesByType("ACCESSIBILITE"));
        stats.setDemandesAccompagnement(statistiqueDAO.countDemandesByType("ACCOMPAGNEMENT"));
        stats.setDemandesAutre(statistiqueDAO.countDemandesByType("AUTRE"));

        return stats;
    }

    // si on veut juste un statut precis
    public int countDemandesByStatut(String statut) {
        return statistiqueDAO.countDemandesByStatut(statut);
    }

    // si on veut juste un type precis
    public int countDemandesByType(String type) {
        return statistiqueDAO.countDemandesByType(type);
    }

    // si on veut juste un statut de reclamation
    public int countReclamationsByStatut(String statut) {
        return statistiqueDAO.countReclamationsByStatut(statut);
    }

    // filtrer par date (utile si on veut par mois par exemple)
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