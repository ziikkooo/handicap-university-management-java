package com.university.handicap.models;

import java.time.LocalDateTime;

public class ArchiveRecord {
    private int id;
    private String type;
    private int referenceId;
    private String description;
    private LocalDateTime dateArchivage;
    private int archivedBy;

    public ArchiveRecord() {}

    public ArchiveRecord(int id, String type, int referenceId,
                         String description, LocalDateTime dateArchivage, int archivedBy) {
        this.id = id;
        this.type = type;
        this.referenceId = referenceId;
        this.description = description;
        this.dateArchivage = dateArchivage;
        this.archivedBy = archivedBy;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public int getReferenceId() { return referenceId; }
    public void setReferenceId(int referenceId) { this.referenceId = referenceId; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDateTime getDateArchivage() { return dateArchivage; }
    public void setDateArchivage(LocalDateTime dateArchivage) { this.dateArchivage = dateArchivage; }
    public int getArchivedBy() { return archivedBy; }
    public void setArchivedBy(int archivedBy) { this.archivedBy = archivedBy; }
}
