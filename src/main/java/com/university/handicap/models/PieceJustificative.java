package com.university.handicap.models;

import java.time.LocalDateTime;

public class PieceJustificative {

    private int id;
    private int demandeId;
    private String fileName;
    private String filePath;
    private LocalDateTime uploadedAt;

    public PieceJustificative() {
    }

    public PieceJustificative(int id, int demandeId, String fileName, String filePath, LocalDateTime uploadedAt) {
        this.id = id;
        this.demandeId = demandeId;
        this.fileName = fileName;
        this.filePath = filePath;
        this.uploadedAt = uploadedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getDemandeId() {
        return demandeId;
    }

    public void setDemandeId(int demandeId) {
        this.demandeId = demandeId;
    }


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }


    @Override
    public String toString() {
        return fileName + " (" + filePath + ")";
    }
}
