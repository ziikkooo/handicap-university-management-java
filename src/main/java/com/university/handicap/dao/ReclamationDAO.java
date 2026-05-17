package com.university.handicap.dao;

import com.university.handicap.config.DatabaseConnection;
import com.university.handicap.models.Reclamation;
import com.university.handicap.models.StatutReclamation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReclamationDAO {

    public boolean createReclamation(Reclamation reclamation) {
        String sql = "INSERT INTO reclamations (user_id, sujet, description, statut) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, reclamation.getUserId());
            statement.setString(2, reclamation.getSujet());
            statement.setString(3, reclamation.getDescription());

            if (reclamation.getStatut() == null) {
                statement.setString(4, StatutReclamation.EN_COURS.name());
            } else {
                statement.setString(4, reclamation.getStatut().name());
            }

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erreur dans createReclamation : " + e.getMessage());
            return false;
        }
    }

    public boolean updateReclamation(Reclamation reclamation) {
        String sql = "UPDATE reclamations SET sujet = ?, description = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, reclamation.getSujet());
            statement.setString(2, reclamation.getDescription());
            statement.setInt(3, reclamation.getId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erreur dans updateReclamation : " + e.getMessage());
            return false;
        }
    }

    public boolean deleteReclamation(int id) {
        String sql = "DELETE FROM reclamations WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erreur dans deleteReclamation : " + e.getMessage());
            return false;
        }
    }

    public boolean changeStatut(int reclamationId, StatutReclamation statut) {
        String sql = "UPDATE reclamations SET statut = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, statut.name());
            statement.setInt(2, reclamationId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erreur dans changeStatutReclamation : " + e.getMessage());
            return false;
        }
    }

    public Reclamation getReclamationById(int id) {
        String sql = "SELECT id, user_id, sujet, description, statut, created_at, updated_at " +
                     "FROM reclamations WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToReclamation(resultSet);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erreur dans getReclamationById : " + e.getMessage());
        }

        return null;
    }

    public List<Reclamation> getAllReclamations() {
        List<Reclamation> reclamations = new ArrayList<>();

        String sql = "SELECT id, user_id, sujet, description, statut, created_at, updated_at " +
                     "FROM reclamations";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                reclamations.add(mapResultSetToReclamation(resultSet));
            }

        } catch (SQLException e) {
            System.out.println("Erreur dans getAllReclamations : " + e.getMessage());
        }

        return reclamations;
    }

    public List<Reclamation> getReclamationsByUser(int userId) {
        List<Reclamation> reclamations = new ArrayList<>();

        String sql = "SELECT id, user_id, sujet, description, statut, created_at, updated_at " +
                     "FROM reclamations WHERE user_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    reclamations.add(mapResultSetToReclamation(resultSet));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erreur dans getReclamationsByUser : " + e.getMessage());
        }

        return reclamations;
    }

    public List<Reclamation> getReclamationsByStatut(StatutReclamation statut) {
        List<Reclamation> reclamations = new ArrayList<>();

        String sql = "SELECT id, user_id, sujet, description, statut, created_at, updated_at " +
                     "FROM reclamations WHERE statut = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, statut.name());

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    reclamations.add(mapResultSetToReclamation(resultSet));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erreur dans getReclamationsByStatut : " + e.getMessage());
        }

        return reclamations;
    }

    private Reclamation mapResultSetToReclamation(ResultSet resultSet) throws SQLException {
        Reclamation reclamation = new Reclamation();

        reclamation.setId(resultSet.getInt("id"));
        reclamation.setUserId(resultSet.getInt("user_id"));
        reclamation.setSujet(resultSet.getString("sujet"));
        reclamation.setDescription(resultSet.getString("description"));
        reclamation.setStatut(StatutReclamation.valueOf(resultSet.getString("statut")));

        Timestamp created = resultSet.getTimestamp("created_at");
        if (created != null) {
            reclamation.setCreatedAt(created.toLocalDateTime());
        }

        Timestamp updated = resultSet.getTimestamp("updated_at");
        if (updated != null) {
            reclamation.setUpdatedAt(updated.toLocalDateTime());
        }

        return reclamation;
    }
}
