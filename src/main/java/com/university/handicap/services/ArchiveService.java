package com.university.handicap.services;

import com.university.handicap.dao.ArchiveDAO;
import com.university.handicap.models.ArchiveRecord;

import java.time.LocalDateTime;
import java.util.List;

public class ArchiveService {

    private final ArchiveDAO archiveDAO = new ArchiveDAO();

    public boolean archiveClosedDemande(int demandeId, String description, int adminId) {
        ArchiveRecord record = new ArchiveRecord();
        record.setType("DEMANDE");
        record.setReferenceId(demandeId);
        record.setDescription(description);
        record.setDateArchivage(LocalDateTime.now());
        record.setArchivedBy(adminId);
        return archiveDAO.archiveRecord(record);
    }

    public boolean archiveClosedReclamation(int reclamationId, String description, int adminId) {
        ArchiveRecord record = new ArchiveRecord();
        record.setType("RECLAMATION");
        record.setReferenceId(reclamationId);
        record.setDescription(description);
        record.setDateArchivage(LocalDateTime.now());
        record.setArchivedBy(adminId);
        return archiveDAO.archiveRecord(record);
    }

    public List<ArchiveRecord> getAllArchives() {
        return archiveDAO.getAllArchives();
    }

    public List<ArchiveRecord> getDemandeArchives() {
        return archiveDAO.getArchivesByType("DEMANDE");
    }

    public List<ArchiveRecord> getReclamationArchives() {
        return archiveDAO.getArchivesByType("RECLAMATION");
    }
}
