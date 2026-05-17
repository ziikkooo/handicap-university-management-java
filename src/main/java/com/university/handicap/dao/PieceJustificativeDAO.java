package com.university.handicap.dao;

import com.university.handicap.config.DatabaseConnection;
import com.university.handicap.models.PieceJustificative;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PieceJustificativeDAO {

    public boolean addPieceJustificative(PieceJustificative piece) {
        String sql = "INSERT INTO pieces_justificatives (demande_id, file_name, file_path) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, piece.getDemandeId());
            statement.setString(2, piece.getFileName());
            statement.setString(3, piece.getFilePath());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erreur dans addPieceJustificative : " + e.getMessage());
            return false;
        }
    }

    public List<PieceJustificative> getPiecesByDemande(int demandeId) {
        List<PieceJustificative> pieces = new ArrayList<>();

        String sql = "SELECT id, demande_id, file_name, file_path, uploaded_at " +
                     "FROM pieces_justificatives WHERE demande_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, demandeId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    pieces.add(mapResultSetToPiece(resultSet));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erreur dans getPiecesByDemande : " + e.getMessage());
        }

        return pieces;
    }

    public boolean deletePieceJustificative(int id) {
        String sql = "DELETE FROM pieces_justificatives WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erreur dans deletePieceJustificative : " + e.getMessage());
            return false;
        }
    }

    private PieceJustificative mapResultSetToPiece(ResultSet resultSet) throws SQLException {
        PieceJustificative piece = new PieceJustificative();

        piece.setId(resultSet.getInt("id"));
        piece.setDemandeId(resultSet.getInt("demande_id"));
        piece.setFileName(resultSet.getString("file_name"));
        piece.setFilePath(resultSet.getString("file_path"));

        Timestamp timestamp = resultSet.getTimestamp("uploaded_at");
        if (timestamp != null) {
            piece.setUploadedAt(timestamp.toLocalDateTime());
        }

        return piece;
    }
}
