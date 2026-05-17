package com.university.handicap;

import com.university.handicap.config.DatabaseConnection;

public class Main {

    public static void main(String[] args) {
        System.out.println("Application démarrée.");

        boolean connected = DatabaseConnection.testConnection();

        if (connected) {
            System.out.println("Connexion à la base de données réussie.");
        } else {
            System.out.println("Échec de la connexion à la base de données.");
        }
    }
}
