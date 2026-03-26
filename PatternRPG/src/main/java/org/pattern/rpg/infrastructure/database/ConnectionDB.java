package org.pattern.rpg.infrastructure.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    private static ConnectionDB instance;
    private static final String URL = "jdbc:sqlite:rpg_save.db";

    private Connection connection;

    private ConnectionDB() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC do SQLite não encontrado.");
        }
    }

    public static synchronized ConnectionDB getInstance() {
        if (instance == null) {
            instance = new ConnectionDB();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL);
            }
            return connection;
        } catch (SQLException e) {
            System.err.println("CRÍTICO: Erro ao conectar ao SQLite (" + URL + "): " + e.getMessage());
            return null;
        }
    }
}
