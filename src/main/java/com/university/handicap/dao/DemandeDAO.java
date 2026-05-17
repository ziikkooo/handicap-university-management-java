package com.university.handicap.dao;

import com.university.handicap.config.DatabaseConnection;
import com.university.handicap.models.Demande;
import com.university.handicap.models.StatutDemande;
import com.university.handicap.models.TypeDemande;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DemandeDAO {

    public boolean create(Demande demande) {
        String sql = "INSERT INTO demandes (user_id, type_demande, description, statut) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, demande.getUserId());
            statement.setString(2, demande.getType().name());
            statement.setString(3, demande.getDescription());
            statement.setString(4, demande.getStatut().name());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erreur dans DemandeDAO.create : " + e.getMessage());
            return false;
        }
    }

    public boolean update(Demande demande) {
        String sql = "UPDATE demandes SET type_demande = ?, statut = ?, description = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, demande.getType().name());
            statement.setString(2, demande.getStatut().name());
            statement.setString(3, demande.getDescription());
            statement.setInt(4, demande.getId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erreur dans DemandeDAO.update : " + e.getMessage());
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM demandes WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erreur dans DemandeDAO.delete : " + e.getMessage());
            return false;
        }
    }

    public List<Demande> findAll() {
        List<Demande> demandes = new ArrayList<>();

        String sql = "SELECT id, user_id, type_demande, description, statut, created_at FROM demandes ORDER BY created_at DESC";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                demandes.add(mapResultSetToDemande(resultSet));
            }

        } catch (SQLException e) {
            System.out.println("Erreur dans DemandeDAO.findAll : " + e.getMessage());
        }

        return demandes;
    }

    public List<Demande> findByUser(int userId) {
        List<Demande> demandes = new ArrayList<>();

        String sql = "SELECT id, user_id, type_demande, description, statut, created_at " +
                     "FROM demandes WHERE user_id = ? ORDER BY created_at DESC";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    demandes.add(mapResultSetToDemande(resultSet));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erreur dans DemandeDAO.findByUser : " + e.getMessage());
        }

        return demandes;
    }

    public List<Demande> findByType(String type) {
        List<Demande> demandes = new ArrayList<>();

        String sql = "SELECT id, user_id, type_demande, description, statut, created_at " +
                     "FROM demandes WHERE type_demande = ? ORDER BY created_at DESC";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, type);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    demandes.add(mapResultSetToDemande(resultSet));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erreur dans DemandeDAO.findByType : " + e.getMessage());
        }

        return demandes;
    }


    public List<Demande> findByType(TypeDemande type) {
        if (type == null) {
            return new ArrayList<>();
        }

        return findByType(type.name());
    }

    private Demande mapResultSetToDemande(ResultSet resultSet) throws SQLException {
        Demande demande = new Demande();

        demande.setId(resultSet.getInt("id"));
        demande.setUserId(resultSet.getInt("user_id"));
        demande.setType(TypeDemande.valueOf(resultSet.getString("type_demande")));
        demande.setDescription(resultSet.getString("description"));
        demande.setStatut(StatutDemande.valueOf(resultSet.getString("statut")));
        demande.setDateCreation(resultSet.getDate("created_at"));

        return demande;
    }
}
