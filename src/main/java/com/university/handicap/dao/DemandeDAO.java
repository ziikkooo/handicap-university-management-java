package com.university.handicap.dao;

import com.university.handicap.models.Demande;
import com.university.handicap.models.TypeDemande;
import com.university.handicap.models.StatutDemande;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DemandeDAO {

    // ── Replace with your project's actual connection class ───
    private Connection getConnection() throws SQLException {
        return DBConnection.getConnection();
    }

    // ── CREATE ────────────────────────────────────────────────

    public boolean create(Demande demande) {
        String sql = "INSERT INTO demandes (user_id, type, statut, description, date_creation) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt      (1, demande.getUserId());
            ps.setString   (2, demande.getType().name());
            ps.setString   (3, demande.getStatut().name());
            ps.setString   (4, demande.getDescription());
            ps.setTimestamp(5, new Timestamp(demande.getDateCreation().getTime()));

            int rows = ps.executeUpdate();

            if (rows > 0) {
                ResultSet keys = ps.getGeneratedKeys();
                if (keys.next()) {
                    demande.setId(keys.getInt(1));
                }
                return true;
            }

        } catch (SQLException e) {
            System.err.println("DemandeDAO.create() error: " + e.getMessage());
        }
        return false;
    }

    // ── UPDATE ────────────────────────────────────────────────

    public boolean update(Demande demande) {
        String sql = "UPDATE demandes SET type = ?, statut = ?, description = ? WHERE id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, demande.getType().name());
            ps.setString(2, demande.getStatut().name());
            ps.setString(3, demande.getDescription());
            ps.setInt   (4, demande.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("DemandeDAO.update() error: " + e.getMessage());
        }
        return false;
    }

    // ── DELETE ────────────────────────────────────────────────

    public boolean delete(int id) {
        String sql = "DELETE FROM demandes WHERE id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("DemandeDAO.delete() error: " + e.getMessage());
        }
        return false;
    }

    // ── FIND ALL ──────────────────────────────────────────────

    public List<Demande> findAll() {
        List<Demande> list = new ArrayList<>();
        String sql = "SELECT * FROM demandes ORDER BY date_creation DESC";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (SQLException e) {
            System.err.println("DemandeDAO.findAll() error: " + e.getMessage());
        }
        return list;
    }

    // ── FIND BY USER ──────────────────────────────────────────

    public List<Demande> findByUser(int userId) {
        List<Demande> list = new ArrayList<>();
        String sql = "SELECT * FROM demandes WHERE user_id = ? ORDER BY date_creation DESC";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (SQLException e) {
            System.err.println("DemandeDAO.findByUser() error: " + e.getMessage());
        }
        return list;
    }

    // ── FIND BY TYPE ──────────────────────────────────────────

    public List<Demande> findByType(TypeDemande type) {
        List<Demande> list = new ArrayList<>();
        String sql = "SELECT * FROM demandes WHERE type = ? ORDER BY date_creation DESC";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, type.name());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (SQLException e) {
            System.err.println("DemandeDAO.findByType() error: " + e.getMessage());
        }
        return list;
    }

    // ── ROW MAPPER (reused by all SELECT methods) ─────────────

    private Demande mapRow(ResultSet rs) throws SQLException {
        Demande d = new Demande();
        d.setId          (rs.getInt      ("id"));
        d.setUserId      (rs.getInt      ("user_id"));
        d.setType        (TypeDemande  .valueOf(rs.getString("type")));
        d.setStatut      (StatutDemande.valueOf(rs.getString("statut")));
        d.setDescription (rs.getString   ("description"));
        d.setDateCreation(rs.getTimestamp("date_creation"));
        return d;
    }
}
