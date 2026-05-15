package com.university.handicap.dao;

import com.university.handicap.config.DatabaseConnection;
import java.sql.*;

// cette classe fait toutes les requetes SQL pour le dashboard
// on touche pas aux autres tables ici, juste SELECT et COUNT
public class StatistiqueDAO {

    private Connection connection;

    public StatistiqueDAO() {
        // on recupere la connexion depuis la classe de person 1
        this.connection = DatabaseConnection.getConnection();
    }

    // compter les demandes selon leur statut (EN_COURS, ACCEPTEE, REFUSEE)
    public int countDemandesByStatut(String statut) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM demandes WHERE statut = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, statut);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            // TODO: faire une vraie gestion d'erreur plus tard
            System.out.println("Erreur countDemandesByStatut : " + e.getMessage());
        }

        return count;
    }

    // compter les demandes selon leur type
    public int countDemandesByType(String type) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM demandes WHERE type_demande = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, type);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.out.println("Erreur countDemandesByType : " + e.getMessage());
        }

        return count;
    }

    // pareil mais pour les reclamations
    public int countReclamationsByStatut(String statut) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM reclamations WHERE statut = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, statut);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.out.println("Erreur countReclamationsByStatut : " + e.getMessage());
        }

        return count;
    }

    // compter les demandes entre deux dates
    // dateDebut et dateFin sont des java.sql.Date
    public int countDemandesBetweenDates(Date dateDebut, Date dateFin) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM demandes WHERE date_creation BETWEEN ? AND ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDate(1, dateDebut);
            stmt.setDate(2, dateFin);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.out.println("Erreur countDemandesBetweenDates : " + e.getMessage());
        }

        return count;
    }

    // stats annuelles pour les demandes
    public int countDemandesByAnnee(int annee) {
        int count = 0;
        // YEAR() c'est une fonction MySQL
        String sql = "SELECT COUNT(*) FROM demandes WHERE YEAR(date_creation) = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, annee);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.out.println("Erreur countDemandesByAnnee : " + e.getMessage());
        }

        return count;
    }

    // pareil pour les reclamations
    public int countReclamationsByAnnee(int annee) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM reclamations WHERE YEAR(date_creation) = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, annee);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.out.println("Erreur countReclamationsByAnnee : " + e.getMessage());
        }

        return count;
    }
}
