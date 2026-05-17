package com.university.handicap;

import com.university.handicap.config.DatabaseConnection;

public class Main {

    public static void main(String[] args) {
        System.out.println("Application started.");

        boolean connected = DatabaseConnection.testConnection();

        if (connected) {
            System.out.println("Connection m3a database khdama.");
        } else {
            System.out.println("Connection m3a database ma khdmatch.");
        }
    }
}
