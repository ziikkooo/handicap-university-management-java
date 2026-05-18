package com.university.handicap.models;

public class DashboardStats {

    // stats des demandes
    private int demandesEnCours;
    private int demandesAcceptees;
    private int demandesRefusees;

    // stats des reclamations
    private int reclamationsEnCours;
    private int reclamationsTraitees;
    private int reclamationsRejetees;

    // stats par type
    private int demandesAmenagement;
    private int demandesAccessibilite;
    private int demandesAccompagnement;
    private int demandesAutre;

    // pour les stats annuelles
    private int annee;
    private int totalDemandesAnnee;
    private int totalReclamationsAnnee;

    // constructeur vide obligatoire
    public DashboardStats() {}

    // --- getters et setters ---
    // j'ai généré ca avec IntelliJ c'est plus rapide lol

    public int getDemandesEnCours() { 
        return demandesEnCours; }
    public void setDemandesEnCours(int demandesEnCours) { 
        this.demandesEnCours = demandesEnCours; }

    public int getDemandesAcceptees() { 
        return demandesAcceptees; }
    public void setDemandesAcceptees(int demandesAcceptees) { 
        this.demandesAcceptees = demandesAcceptees; }

    public int getDemandesRefusees() { 
        return demandesRefusees; }
    public void setDemandesRefusees(int demandesRefusees) { 
        this.demandesRefusees = demandesRefusees; }

    public int getReclamationsEnCours() { 
        return reclamationsEnCours; }
    public void setReclamationsEnCours(int reclamationsEnCours) { 
        this.reclamationsEnCours = reclamationsEnCours; }

    public int getReclamationsTraitees() { 
        return reclamationsTraitees; }
    public void setReclamationsTraitees(int reclamationsTraitees) { 
        this.reclamationsTraitees = reclamationsTraitees; }

    public int getReclamationsRejetees() { 
        return reclamationsRejetees; }
    public void setReclamationsRejetees(int reclamationsRejetees) { 
        this.reclamationsRejetees = reclamationsRejetees; }

    public int getDemandesAmenagement() { return demandesAmenagement; }
    public void setDemandesAmenagement(int d) { this.demandesAmenagement = d; }

    public int getDemandesAccessibilite() { 
        return demandesAccessibilite; }
    public void setDemandesAccessibilite(int d) { 
        this.demandesAccessibilite = d; }

    public int getDemandesAccompagnement() { 
        return demandesAccompagnement; }
    public void setDemandesAccompagnement(int d) { 
        this.demandesAccompagnement = d; }

    public int getDemandesAutre() { 
        return demandesAutre; }
    public void setDemandesAutre(int d) { 
        this.demandesAutre = d; }

    public int getAnnee() { 
        return annee; }
    public void setAnnee(int annee) { 
        this.annee = annee; }

    public int getTotalDemandesAnnee() { 
        return totalDemandesAnnee; }
    public void setTotalDemandesAnnee(int t) { 
        this.totalDemandesAnnee = t; }

    public int getTotalReclamationsAnnee() { 
        return totalReclamationsAnnee; }
    public void setTotalReclamationsAnnee(int t) { 
        this.totalReclamationsAnnee = t; }

    
    @Override
    public String toString() {
        return "DashboardStats{" +
                "demandesEnCours=" + demandesEnCours +
                ", demandesAcceptees=" + demandesAcceptees +
                ", demandesRefusees=" + demandesRefusees +
                ", reclamationsEnCours=" + reclamationsEnCours +
                ", reclamationsTraitees=" + reclamationsTraitees +
                ", reclamationsRejetees=" + reclamationsRejetees +
                '}';
    }
}
