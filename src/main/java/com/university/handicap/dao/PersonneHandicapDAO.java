package com.university.handicap.dao;

import com.university.handicap.config.DatabaseConnection;
import com.university.handicap.models.PersonneHandicap;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonneHandicapDAO {

    public boolean addPersonne(PersonneHandicap personne) {
        String sql = "INSERT INTO personnes_handicap (user_id, telephone, type_handicap, filiere, niveau) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, personne.getUserId());
            statement.setString(2, personne.getTelephone());
            statement.setString(3, personne.getTypeHandicap());
            statement.setString(4, personne.getFiliere());
            statement.setString(5, personne.getNiveau());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erreur dans addPersonne : " + e.getMessage());
            return false;
        }
    }

    public boolean updatePersonne(PersonneHandicap personne) {
        String updateUser = "UPDATE users SET nom = ?, prenom = ?, email = ? WHERE id = ?";
        String updateProfile = "UPDATE personnes_handicap SET telephone = ?, type_handicap = ?, filiere = ?, niveau = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);

            try (PreparedStatement userStatement = connection.prepareStatement(updateUser);
                 PreparedStatement profileStatement = connection.prepareStatement(updateProfile)) {

                // hna kanbedlo les infos li kaynin f table users
                userStatement.setString(1, personne.getNom());
                userStatement.setString(2, personne.getPrenom());
                userStatement.setString(3, personne.getEmail());
                userStatement.setInt(4, personne.getUserId());
                userStatement.executeUpdate();

                // hna kanbedlo les infos dyal profile
                profileStatement.setString(1, personne.getTelephone());
                profileStatement.setString(2, personne.getTypeHandicap());
                profileStatement.setString(3, personne.getFiliere());
                profileStatement.setString(4, personne.getNiveau());
                profileStatement.setInt(5, personne.getId());
                int rows = profileStatement.executeUpdate();

                connection.commit();
                return rows > 0;
            } catch (SQLException e) {
                connection.rollback();
                System.out.println("Erreur dans updatePersonne : " + e.getMessage());
                return false;
            } finally {
                connection.setAutoCommit(true);
            }

        } catch (SQLException e) {
            System.out.println("Erreur dans connection dyal updatePersonne : " + e.getMessage());
            return false;
        }
    }

    public boolean deletePersonne(int id) {
        String sql = "DELETE FROM personnes_handicap WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erreur dans deletePersonne : " + e.getMessage());
            return false;
        }
    }

    public PersonneHandicap getPersonneById(int id) {
        String sql = "SELECT ph.id, ph.user_id, u.nom, u.prenom, u.email, " +
                     "ph.telephone, ph.type_handicap, ph.filiere, ph.niveau " +
                     "FROM personnes_handicap ph " +
                     "JOIN users u ON ph.user_id = u.id " +
                     "WHERE ph.id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToPersonne(resultSet);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erreur dans getPersonneById : " + e.getMessage());
        }

        return null;
    }

    public PersonneHandicap getPersonneByUserId(int userId) {
        String sql = "SELECT ph.id, ph.user_id, u.nom, u.prenom, u.email, " +
                     "ph.telephone, ph.type_handicap, ph.filiere, ph.niveau " +
                     "FROM personnes_handicap ph " +
                     "JOIN users u ON ph.user_id = u.id " +
                     "WHERE ph.user_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToPersonne(resultSet);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erreur dans getPersonneByUserId : " + e.getMessage());
        }

        return null;
    }

    public List<PersonneHandicap> getAllPersonnes() {
        List<PersonneHandicap> personnes = new ArrayList<>();

        String sql = "SELECT ph.id, ph.user_id, u.nom, u.prenom, u.email, " +
                     "ph.telephone, ph.type_handicap, ph.filiere, ph.niveau " +
                     "FROM personnes_handicap ph " +
                     "JOIN users u ON ph.user_id = u.id";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                personnes.add(mapResultSetToPersonne(resultSet));
            }

        } catch (SQLException e) {
            System.out.println("Erreur dans getAllPersonnes : " + e.getMessage());
        }

        return personnes;
    }

    public List<PersonneHandicap> searchPersonnes(String keyword) {
        List<PersonneHandicap> personnes = new ArrayList<>();

        String sql = "SELECT ph.id, ph.user_id, u.nom, u.prenom, u.email, " +
                     "ph.telephone, ph.type_handicap, ph.filiere, ph.niveau " +
                     "FROM personnes_handicap ph " +
                     "JOIN users u ON ph.user_id = u.id " +
                     "WHERE u.nom LIKE ? OR u.prenom LIKE ? OR u.email LIKE ? " +
                     "OR ph.type_handicap LIKE ? OR ph.filiere LIKE ? OR ph.niveau LIKE ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            String searchValue = "%" + keyword + "%";

            statement.setString(1, searchValue);
            statement.setString(2, searchValue);
            statement.setString(3, searchValue);
            statement.setString(4, searchValue);
            statement.setString(5, searchValue);
            statement.setString(6, searchValue);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    personnes.add(mapResultSetToPersonne(resultSet));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erreur dans searchPersonnes : " + e.getMessage());
        }

        return personnes;
    }

    private PersonneHandicap mapResultSetToPersonne(ResultSet resultSet) throws SQLException {
        PersonneHandicap personne = new PersonneHandicap();

        personne.setId(resultSet.getInt("id"));
        personne.setUserId(resultSet.getInt("user_id"));
        personne.setNom(resultSet.getString("nom"));
        personne.setPrenom(resultSet.getString("prenom"));
        personne.setEmail(resultSet.getString("email"));
        personne.setTelephone(resultSet.getString("telephone"));
        personne.setTypeHandicap(resultSet.getString("type_handicap"));
        personne.setFiliere(resultSet.getString("filiere"));
        personne.setNiveau(resultSet.getString("niveau"));

        return personne;
    }
}
