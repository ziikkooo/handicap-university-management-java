package com.university.handicap.dao;

import com.university.handicap.config.DatabaseConnection;
import com.university.handicap.models.ArchiveRecord;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArchiveDAO {

    public boolean archiveRecord(ArchiveRecord record) {
        String sql = "INSERT INTO archives (type, reference_id, description, date_archivage, archived_by) VALUES (?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, record.getType());
            ps.setInt(2, record.getReferenceId());
            ps.setString(3, record.getDescription());
            ps.setTimestamp(4, Timestamp.valueOf(record.getDateArchivage()));
            ps.setInt(5, record.getArchivedBy());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[ArchiveDAO] archiveRecord: " + e.getMessage());
            return false;
        }
    }

    public List<ArchiveRecord> getAllArchives() {
        List<ArchiveRecord> list = new ArrayList<>();
        String sql = "SELECT * FROM archives ORDER BY date_archivage DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            System.err.println("[ArchiveDAO] getAllArchives: " + e.getMessage());
        }
        return list;
    }

    public List<ArchiveRecord> getArchivesByType(String type) {
        List<ArchiveRecord> list = new ArrayList<>();
        String sql = "SELECT * FROM archives WHERE type = ? ORDER BY date_archivage DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            System.err.println("[ArchiveDAO] getArchivesByType: " + e.getMessage());
        }
        return list;
    }

    private ArchiveRecord mapRow(ResultSet rs) throws SQLException {
        ArchiveRecord a = new ArchiveRecord();
        a.setId(rs.getInt("id"));
        a.setType(rs.getString("type"));
        a.setReferenceId(rs.getInt("reference_id"));
        a.setDescription(rs.getString("description"));
        a.setDateArchivage(rs.getTimestamp("date_archivage").toLocalDateTime());
        a.setArchivedBy(rs.getInt("archived_by"));
        return a;
    }
}
