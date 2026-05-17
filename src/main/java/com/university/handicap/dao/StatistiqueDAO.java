package com.university.handicap.dao;

import com.university.handicap.config.DatabaseConnection;
import com.university.handicap.models.DashboardStats;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatistiqueDAO {

    public DashboardStats getDashboardStats() {
        DashboardStats stats = new DashboardStats();

        stats.setDemandesEnCours(countDemandesByStatut("EN_COURS"));
        stats.setDemandesAcceptees(countDemandesByStatut("ACCEPTEE"));
        stats.setDemandesRefusees(countDemandesByStatut("REFUSEE"));

        stats.setReclamationsEnCours(countReclamationsByStatut("EN_COURS"));
        stats.setReclamationsTraitees(countReclamationsByStatut("TRAITEE"));
        stats.setReclamationsRejetees(countReclamationsByStatut("REJETEE"));

        return stats;
    }

    public int countDemandesByStatut(String statut) {
        String sql = "SELECT COUNT(*) FROM demandes WHERE statut = ?";
        return countWithOneParameter(sql, statut);
    }

    public int countDemandesByType(String typeDemande) {
        String sql = "SELECT COUNT(*) FROM demandes WHERE type_demande = ?";
        return countWithOneParameter(sql, typeDemande);
    }

    public int countReclamationsByStatut(String statut) {
        String sql = "SELECT COUNT(*) FROM reclamations WHERE statut = ?";
        return countWithOneParameter(sql, statut);
    }

    public int countAllDemandes() {
        String sql = "SELECT COUNT(*) FROM demandes";
        return countSimple(sql);
    }

    public int countAllReclamations() {
        String sql = "SELECT COUNT(*) FROM reclamations";
        return countSimple(sql);
    }

    public int countDemandesBetweenDates(Date dateDebut, Date dateFin) {
        String sql = "SELECT COUNT(*) FROM demandes WHERE DATE(created_at) BETWEEN ? AND ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, dateDebut);
            statement.setDate(2, dateFin);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erreur dans countDemandesBetweenDates : " + e.getMessage());
        }

        return 0;
    }

    public int countDemandesByAnnee(int annee) {
        String sql = "SELECT COUNT(*) FROM demandes WHERE YEAR(created_at) = ?";
        return countWithOneIntParameter(sql, annee);
    }

    public int countReclamationsByAnnee(int annee) {
        String sql = "SELECT COUNT(*) FROM reclamations WHERE YEAR(created_at) = ?";
        return countWithOneIntParameter(sql, annee);
    }

    private int countSimple(String sql) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println("Erreur dans statistique : " + e.getMessage());
        }

        return 0;
    }

    private int countWithOneParameter(String sql, String value) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, value);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erreur dans statistique : " + e.getMessage());
        }

        return 0;
    }

    private int countWithOneIntParameter(String sql, int value) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, value);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erreur dans statistique : " + e.getMessage());
        }

        return 0;
    }
}
