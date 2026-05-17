package com.university.handicap.dao;

import com.university.handicap.config.DatabaseConnection;
import com.university.handicap.models.HistoriqueDemande;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoriqueDemandeDAO {

    public boolean addHistorique(HistoriqueDemande historique) {
        String sql = "INSERT INTO historique_demandes " +
                     "(demande_id, admin_id, ancien_statut, nouveau_statut, action) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, historique.getDemandeId());

            if (historique.getAdminId() > 0) {
                statement.setInt(2, historique.getAdminId());
            } else {
                statement.setNull(2, Types.INTEGER);
            }

            statement.setString(3, historique.getAncienStatut());
            statement.setString(4, historique.getNouveauStatut());
            statement.setString(5, historique.getAction());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Mouchkil f addHistoriqueDemande: " + e.getMessage());
            return false;
        }
    }

    public List<HistoriqueDemande> getHistoriqueByDemande(int demandeId) {
        List<HistoriqueDemande> historiques = new ArrayList<>();

        String sql = "SELECT id, demande_id, admin_id, ancien_statut, nouveau_statut, action, action_date " +
                     "FROM historique_demandes WHERE demande_id = ? ORDER BY action_date DESC";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, demandeId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    historiques.add(mapResultSetToHistorique(resultSet));
                }
            }

        } catch (SQLException e) {
            System.out.println("Mouchkil f getHistoriqueByDemande: " + e.getMessage());
        }

        return historiques;
    }

    private HistoriqueDemande mapResultSetToHistorique(ResultSet resultSet) throws SQLException {
        HistoriqueDemande historique = new HistoriqueDemande();

        historique.setId(resultSet.getInt("id"));
        historique.setDemandeId(resultSet.getInt("demande_id"));
        historique.setAdminId(resultSet.getInt("admin_id"));
        historique.setAncienStatut(resultSet.getString("ancien_statut"));
        historique.setNouveauStatut(resultSet.getString("nouveau_statut"));
        historique.setAction(resultSet.getString("action"));

        Timestamp timestamp = resultSet.getTimestamp("action_date");
        if (timestamp != null) {
            historique.setActionDate(timestamp.toLocalDateTime());
        }

        return historique;
    }
}
