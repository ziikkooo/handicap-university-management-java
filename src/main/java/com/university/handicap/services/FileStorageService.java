package com.university.handicap.services;

import com.university.handicap.models.PieceJustificative;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileStorageService {

    private static final String UPLOAD_DIR = "uploads/demandes";

    public PieceJustificative saveFile(int demandeId, File sourceFile) {
        if (sourceFile == null || !sourceFile.exists()) {
            return null;
        }

        try {
            Path demandeFolder = Path.of(UPLOAD_DIR, String.valueOf(demandeId));
            Files.createDirectories(demandeFolder);

            String originalName = sourceFile.getName();
            String newName = System.currentTimeMillis() + "_" + originalName;
            Path destination = demandeFolder.resolve(newName);

            Files.copy(sourceFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);

            PieceJustificative piece = new PieceJustificative();
            piece.setDemandeId(demandeId);
            piece.setFileName(originalName);
            piece.setFilePath(destination.toString());

            return piece;

        } catch (IOException e) {
            System.out.println("Mouchkil f saveFile: " + e.getMessage());
            return null;
        }
    }
}
