package org.pattern.rpg.infrastructure.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    private static ConnectionDB instance;
    private static final String URL = "jdbc:postgresql://localhost:5432/game_saves";
    private static final String USER = "postgres";
    private static final String PASSWORD = "142536";

    private Connection connection;

    private ConnectionDB() {}

    public static synchronized ConnectionDB getInstance() {
        if (instance == null) {
            instance = new ConnectionDB();
        }
        return instance;
    }

    public Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                System.out.println("Database connection error:");
                System.out.println(e.getMessage());
                return null;
            }
        }
        return connection;
    }
}