package org.pattern.rpg.infrastructure.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

    private static ConnectionDB instance;

    private static final String URL = "jdbc:postgresql://localhost:5432/game_saves";
    private static final String USER = "postgres";
    private static final String PASSWORD = "142536";

    private ConnectionDB() {
    }

    public static synchronized ConnectionDB getInstance() {
        if (instance == null) {
            instance = new ConnectionDB();
            System.out.println("Instância única de conexão criada com sucesso!");
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conectado ao banco de dados com sucesso!");
            return connection;
        } catch (SQLException e) {
            System.out.println("Erro ao conectar com o banco de dados!");
            System.out.println(e.getMessage());
            return null;
        }
    }
}