package org.pattern.rpg.infrastructure.database;

import org.pattern.rpg.domain.entity.Save;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SaveRepository {

    public void createSave(Save save) {
        String sql = "INSERT INTO saves (item, pfloor, score) VALUES (?, ?, ?)";

        Connection connection = ConnectionDB.getInstance().getConnection();

        if (connection == null) {
            System.out.println("conexão falhou");
            return;
        }

        try (connection; PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setArray(1, connection.createArrayOf("text", save.getItems()));
            statement.setInt(2, save.getFloor());
            statement.setInt(3, save.getScore());

            statement.executeUpdate();
            System.out.println("Save criado");

        } catch (SQLException e) {
            System.out.println("Erro ao criar");
            System.out.println(e.getMessage());
        }
    }

    public void overwriteSave(Save save) {
        String sql = "UPDATE saves SET item = ?, pfloor = ?, score = ? WHERE id = ?";

        Connection connection = ConnectionDB.getInstance().getConnection();

        if (connection == null) {
            System.out.println("conexão falhou.");
            return;
        }

        try (connection; PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setArray(1, connection.createArrayOf("text", save.getItems()));
            statement.setInt(2, save.getFloor());
            statement.setInt(3, save.getScore());
            statement.setInt(4, save.getId());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Save sobrescrito com sucesso!");
            } else {
                System.out.println("Nenhum save foi encontrado com esse id para sobrescrever.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao sobrescrever o save!");
            System.out.println(e.getMessage());
        }
    }

    public Save loadSave(int id) {
        String sql = "SELECT * FROM saves WHERE id = ?";

        Connection connection = ConnectionDB.getInstance().getConnection();

        if (connection == null) {
            System.out.println(" conexão falhou");
            return null;
        }

        try (connection; PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int saveId = resultSet.getInt("id");
                    int floor = resultSet.getInt("pfloor");
                    int score = resultSet.getInt("score");

                    Array itemArray = resultSet.getArray("item");
                    String[] items = (String[]) itemArray.getArray();

                    System.out.println("Save carregado com sucesso!");
                    return new Save(saveId, items, floor, score);
                } else {
                    System.out.println("Nenhum save foi encontrado");
                    return null;
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao carregar");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void deleteSave(int id) {
        String sql = "DELETE FROM saves WHERE id = ?";

        Connection connection = ConnectionDB.getInstance().getConnection();

        if (connection == null) {
            System.out.println("conexão falhou.");
            return;
        }

        try (connection; PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Save deletado");
            } else {
                System.out.println("Nenhum save foi encontrado");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao deletar");
            System.out.println(e.getMessage());
        }
    }
}