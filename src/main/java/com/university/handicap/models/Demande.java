package com.university.handicap.models;

import java.util.Date;

public class Demande {

    private int id;
    private int userId;
    private TypeDemande type;
    private StatutDemande statut;
    private String description;
    private Date dateCreation;

    // ── Constructors ──────────────────────────────────────────

    public Demande() {}

    public Demande(int userId, TypeDemande type, String description) {
        this.userId       = userId;
        this.type         = type;
        this.description  = description;
        this.statut       = StatutDemande.EN_COURS;
        this.dateCreation = new Date();
    }

    // ── Getters & Setters ─────────────────────────────────────

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public TypeDemande getType() { return type; }
    public void setType(TypeDemande type) { this.type = type; }

    public StatutDemande getStatut() { return statut; }
    public void setStatut(StatutDemande statut) { this.statut = statut; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Date getDateCreation() { return dateCreation; }
    public void setDateCreation(Date dateCreation) { this.dateCreation = dateCreation; }

    @Override
    public String toString() {
        return "Demande{"
             + "id=" + id
             + ", userId=" + userId
             + ", type=" + type
             + ", statut=" + statut
             + ", description='" + description + "'"
             + ", dateCreation=" + dateCreation
             + "}";
    }
}
