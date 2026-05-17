package com.university.handicap;

import com.university.handicap.config.DatabaseConnection;
import com.university.handicap.views.AppTheme;
import com.university.handicap.views.LoginFrame;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        System.out.println("Application démarrée.");

        boolean connected = DatabaseConnection.testConnection();

        if (connected) {
            System.out.println("Connexion à la base de données réussie.");
        } else {
            System.out.println("Échec de la connexion à la base de données.");
        }

        SwingUtilities.invokeLater(() -> {
            AppTheme.install();
            new LoginFrame().setVisible(true);
        });
    }
}
