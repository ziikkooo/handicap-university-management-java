package com.university.handicap.models;

import java.time.LocalDateTime;

public class HistoriqueReclamation {

    private int id;
    private int reclamationId;
    private int adminId;
    private String ancienStatut;
    private String nouveauStatut;
    private String action;
    private LocalDateTime actionDate;

    public HistoriqueReclamation() {
    }

    public HistoriqueReclamation(int id, int reclamationId, int adminId,
                                 String ancienStatut, String nouveauStatut,
                                 String action, LocalDateTime actionDate) {
        this.id = id;
        this.reclamationId = reclamationId;
        this.adminId = adminId;
        this.ancienStatut = ancienStatut;
        this.nouveauStatut = nouveauStatut;
        this.action = action;
        this.actionDate = actionDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getReclamationId() {
        return reclamationId;
    }

    public void setReclamationId(int reclamationId) {
        this.reclamationId = reclamationId;
    }


    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }


    public String getAncienStatut() {
        return ancienStatut;
    }

    public void setAncienStatut(String ancienStatut) {
        this.ancienStatut = ancienStatut;
    }


    public String getNouveauStatut() {
        return nouveauStatut;
    }

    public void setNouveauStatut(String nouveauStatut) {
        this.nouveauStatut = nouveauStatut;
    }


    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }


    public LocalDateTime getActionDate() {
        return actionDate;
    }

    public void setActionDate(LocalDateTime actionDate) {
        this.actionDate = actionDate;
    }


    @Override
    public String toString() {
        return "Reclamation " + reclamationId + " : " + ancienStatut + " -> " + nouveauStatut;
    }
}
