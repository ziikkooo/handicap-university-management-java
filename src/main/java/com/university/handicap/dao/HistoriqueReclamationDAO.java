package com.university.handicap.dao;

import com.university.handicap.config.DatabaseConnection;
import com.university.handicap.models.HistoriqueReclamation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoriqueReclamationDAO {

    public boolean addHistorique(HistoriqueReclamation historique) {
        String sql = "INSERT INTO historique_reclamations " +
                     "(reclamation_id, admin_id, ancien_statut, nouveau_statut, action) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, historique.getReclamationId());

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
            System.out.println("Erreur dans addHistoriqueReclamation : " + e.getMessage());
            return false;
        }
    }

    public List<HistoriqueReclamation> getHistoriqueByReclamation(int reclamationId) {
        List<HistoriqueReclamation> historiques = new ArrayList<>();

        String sql = "SELECT id, reclamation_id, admin_id, ancien_statut, nouveau_statut, action, action_date " +
                     "FROM historique_reclamations WHERE reclamation_id = ? ORDER BY action_date DESC";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, reclamationId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    historiques.add(mapResultSetToHistorique(resultSet));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erreur dans getHistoriqueByReclamation : " + e.getMessage());
        }

        return historiques;
    }

    private HistoriqueReclamation mapResultSetToHistorique(ResultSet resultSet) throws SQLException {
        HistoriqueReclamation historique = new HistoriqueReclamation();

        historique.setId(resultSet.getInt("id"));
        historique.setReclamationId(resultSet.getInt("reclamation_id"));
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
