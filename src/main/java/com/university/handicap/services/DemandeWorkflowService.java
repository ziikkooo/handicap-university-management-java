package com.university.handicap.services;

import com.university.handicap.config.DatabaseConnection;
import com.university.handicap.dao.HistoriqueDemandeDAO;
import com.university.handicap.dao.PieceJustificativeDAO;
import com.university.handicap.models.HistoriqueDemande;
import com.university.handicap.models.PieceJustificative;

import java.io.File;
import java.sql.*;
import java.util.List;

public class DemandeWorkflowService {

    private final PieceJustificativeDAO pieceDAO;
    private final HistoriqueDemandeDAO historiqueDAO;
    private final FileStorageService fileStorageService;

    public DemandeWorkflowService() {
        this.pieceDAO = new PieceJustificativeDAO();
        this.historiqueDAO = new HistoriqueDemandeDAO();
        this.fileStorageService = new FileStorageService();
    }

    public boolean addPieceJustificative(int demandeId, File file) {
        if (demandeId <= 0 || file == null) {
            return false;
        }

        PieceJustificative piece = fileStorageService.saveFile(demandeId, file);

        if (piece == null) {
            return false;
        }

        return pieceDAO.addPieceJustificative(piece);
    }

    public boolean addPieceJustificativePath(int demandeId, String fileName, String filePath) {
        if (demandeId <= 0 || isEmpty(fileName) || isEmpty(filePath)) {
            return false;
        }

        PieceJustificative piece = new PieceJustificative();
        piece.setDemandeId(demandeId);
        piece.setFileName(fileName);
        piece.setFilePath(filePath);

        return pieceDAO.addPieceJustificative(piece);
    }

    public boolean changeStatut(int demandeId, String nouveauStatut, int adminId) {
        if (demandeId <= 0 || !isValidStatut(nouveauStatut)) {
            return false;
        }

        String ancienStatut = getStatutDemande(demandeId);

        if (ancienStatut == null) {
            return false;
        }

        String sql = "UPDATE demandes SET statut = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, nouveauStatut);
            statement.setInt(2, demandeId);

            boolean updated = statement.executeUpdate() > 0;

            if (updated) {
                HistoriqueDemande historique = new HistoriqueDemande();
                historique.setDemandeId(demandeId);
                historique.setAdminId(adminId);
                historique.setAncienStatut(ancienStatut);
                historique.setNouveauStatut(nouveauStatut);
                historique.setAction("Changement du statut de la demande");

                historiqueDAO.addHistorique(historique);
            }

            return updated;

        } catch (SQLException e) {
            System.out.println("Erreur dans changeStatut : " + e.getMessage());
            return false;
        }
    }

    public List<PieceJustificative> getPiecesByDemande(int demandeId) {
        return pieceDAO.getPiecesByDemande(demandeId);
    }

    public List<HistoriqueDemande> getHistoriqueDemande(int demandeId) {
        return historiqueDAO.getHistoriqueByDemande(demandeId);
    }

    private String getStatutDemande(int demandeId) {
        String sql = "SELECT statut FROM demandes WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, demandeId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("statut");
                }
            }

        } catch (SQLException e) {
            System.out.println("Erreur dans getStatutDemande : " + e.getMessage());
        }

        return null;
    }

    private boolean isValidStatut(String statut) {
        return "EN_COURS".equals(statut)
                || "ACCEPTEE".equals(statut)
                || "REFUSEE".equals(statut);
    }

    private boolean isEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }
}
