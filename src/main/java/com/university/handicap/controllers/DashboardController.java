package com.university.handicap.controllers;

import com.university.handicap.models.DashboardStats;
import com.university.handicap.services.DashboardService;
import java.sql.Date;

// controller du dashboard
// c'est lui qui sera appele depuis la vue Swing
// il fait rien de special a part appeler le service
public class DashboardController {

    private DashboardService dashboardService;

    public DashboardController() {
        this.dashboardService = new DashboardService();
    }

    // recuperer toutes les stats (pour afficher le dashboard)
    public DashboardStats getAllStats() {
        return dashboardService.getAllStats();
    }

    // compter demandes par statut
    public int countDemandesByStatut(String statut) {
        return dashboardService.countDemandesByStatut(statut);
    }

    // compter demandes par type
    public int countDemandesByType(String type) {
        return dashboardService.countDemandesByType(type);
    }

    // compter reclamations par statut
    public int countReclamationsByStatut(String statut) {
        return dashboardService.countReclamationsByStatut(statut);
    }

    // demandes entre deux dates
    public int getDemandesBetweenDates(Date dateDebut, Date dateFin) {
        return dashboardService.getDemandesBetweenDates(dateDebut, dateFin);
    }

    // stats annuelles
    public DashboardStats getAnnualStats(int annee) {
        return dashboardService.getAnnualStats(annee);
    }
}