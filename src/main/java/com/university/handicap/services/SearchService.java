package com.university.handicap.services;

import com.university.handicap.config.DatabaseConnection;
import com.university.handicap.models.SearchCriteria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SearchService {

    public List<ResultSet> searchDemandes(SearchCriteria criteria) {
        List<Object> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT d.*, u.nom, u.prenom, u.email FROM demandes d " +
            "JOIN users u ON d.user_id = u.id WHERE 1=1"
        );
        if (criteria.getNom() != null && !criteria.getNom().isBlank()) {
            sql.append(" AND u.nom LIKE ?");
            params.add("%" + criteria.getNom() + "%");
        }
        if (criteria.getEmail() != null && !criteria.getEmail().isBlank()) {
            sql.append(" AND u.email LIKE ?");
            params.add("%" + criteria.getEmail() + "%");
        }
        if (criteria.getType() != null && !criteria.getType().isBlank()) {
            sql.append(" AND d.type = ?");
            params.add(criteria.getType());
        }
        if (criteria.getStatut() != null && !criteria.getStatut().isBlank()) {
            sql.append(" AND d.statut = ?");
            params.add(criteria.getStatut());
        }
        if (criteria.getKeyword() != null && !criteria.getKeyword().isBlank()) {
            sql.append(" AND (d.description LIKE ? OR d.titre LIKE ?)");
            params.add("%" + criteria.getKeyword() + "%");
            params.add("%" + criteria.getKeyword() + "%");
        }
        if (criteria.getDateDebut() != null) {
            sql.append(" AND d.date_creation >= ?");
            params.add(Date.valueOf(criteria.getDateDebut()));
        }
        if (criteria.getDateFin() != null) {
            sql.append(" AND d.date_creation <= ?");
            params.add(Date.valueOf(criteria.getDateFin()));
        }
        return executeSearch(sql.toString(), params);
    }

    public List<ResultSet> searchReclamations(SearchCriteria criteria) {
        List<Object> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT r.*, u.nom, u.prenom, u.email FROM reclamations r " +
            "JOIN users u ON r.user_id = u.id WHERE 1=1"
        );
        if (criteria.getNom() != null && !criteria.getNom().isBlank()) {
            sql.append(" AND u.nom LIKE ?");
            params.add("%" + criteria.getNom() + "%");
        }
        if (criteria.getEmail() != null && !criteria.getEmail().isBlank()) {
            sql.append(" AND u.email LIKE ?");
            params.add("%" + criteria.getEmail() + "%");
        }
        if (criteria.getStatut() != null && !criteria.getStatut().isBlank()) {
            sql.append(" AND r.statut = ?");
            params.add(criteria.getStatut());
        }
        if (criteria.getKeyword() != null && !criteria.getKeyword().isBlank()) {
            sql.append(" AND r.description LIKE ?");
            params.add("%" + criteria.getKeyword() + "%");
        }
        if (criteria.getDateDebut() != null) {
            sql.append(" AND r.date_creation >= ?");
            params.add(Date.valueOf(criteria.getDateDebut()));
        }
        if (criteria.getDateFin() != null) {
            sql.append(" AND r.date_creation <= ?");
            params.add(Date.valueOf(criteria.getDateFin()));
        }
        return executeSearch(sql.toString(), params);
    }

    public List<ResultSet> getCompleteHistoryByUser(int userId) {
        List<Object> params = new ArrayList<>();
        String sql = "SELECT 'DEMANDE' as source, id, description, statut, date_creation " +
                     "FROM demandes WHERE user_id = ? " +
                     "UNION ALL " +
                     "SELECT 'RECLAMATION' as source, id, description, statut, date_creation " +
                     "FROM reclamations WHERE user_id = ? " +
                     "ORDER BY date_creation DESC";
        params.add(userId);
        params.add(userId);
        return executeSearch(sql, params);
    }

    private List<ResultSet> executeSearch(String sql, List<Object> params) {
        List<ResultSet> results = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            results.add(ps.executeQuery());
        } catch (SQLException e) {
            System.err.println("[SearchService] executeSearch: " + e.getMessage());
        }
        return results;
    }
}
