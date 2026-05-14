package com.university.handicap.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Had l information dyal connection m3a MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/handicap_university_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private DatabaseConnection() {
        // Constructor khawi bach ma ndirosh object mn had class
    }

    public static Connection getConnection() throws SQLException {
        // Hna Java kat7el connection m3a database
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static boolean testConnection() {
        // Method sghira bach njarbo wach database khdama
        try (Connection connection = getConnection()) {
            return connection != null;
        } catch (SQLException e) {
            System.out.println("Mouchkil f connection m3a database: " + e.getMessage());
            return false;
        }
    }
}
