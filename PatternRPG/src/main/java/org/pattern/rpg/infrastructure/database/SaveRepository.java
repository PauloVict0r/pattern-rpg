package org.pattern.rpg.infrastructure.database;

import org.pattern.rpg.domain.entity.Save;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SaveRepository {

    public void createSave(Save save) {
        String sql = "INSERT INTO saves (item, pfloor, score) VALUES (?, ?, ?)";
        Connection connection = ConnectionDB.getInstance().getConnection();

        // Apenas o PreparedStatement no try-with-resources para não fechar a Connection singleton
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            // Convertendo o array de String para uma única String separada por vírgulas
            stmt.setString(1, String.join(",", save.getItems()));
            stmt.setInt(2, save.getFloor());
            stmt.setInt(3, save.getScore());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void overwriteSave(Save save) {
        String sql = "UPDATE saves SET item=?, pfloor=?, score=? WHERE id=?";

        Connection connection = ConnectionDB.getInstance().getConnection();

        try (connection; PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setArray(1, connection.createArrayOf("text", save.getItems()));
            stmt.setInt(2, save.getFloor());
            stmt.setInt(3, save.getScore());
            stmt.setInt(4, save.getId());

            stmt.executeUpdate();
            System.out.println("Save overwritten!");

        } catch (SQLException e) {
            System.out.println("Error overwriting save:");
            System.out.println(e.getMessage());
        }
    }

    public Save loadSave(int id) {
        String sql = "SELECT * FROM saves WHERE id=?";

        Connection connection = ConnectionDB.getInstance().getConnection();

        try (connection; PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int floor = rs.getInt("pfloor");
                int score = rs.getInt("score");

                Array array = rs.getArray("item");
                String[] items = (String[]) array.getArray();

                return new Save(id, items, floor, score);
            }

        } catch (SQLException e) {
            System.out.println("Error loading save:");
            System.out.println(e.getMessage());
        }

        return null;
    }

    public List<Save> listAllSaves() {

        List<Save> saves = new ArrayList<>();

        String sql = "SELECT * FROM saves ORDER BY id";

        Connection connection = ConnectionDB.getInstance().getConnection();

        try (connection; PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                int id = rs.getInt("id");
                int floor = rs.getInt("pfloor");
                int score = rs.getInt("score");

                Array array = rs.getArray("item");
                String[] items = (String[]) array.getArray();

                saves.add(new Save(id, items, floor, score));
            }

        } catch (SQLException e) {
            System.out.println("Error listing saves:");
            System.out.println(e.getMessage());
        }

        return saves;
    }

    public void deleteSave(int id) {

        String sql = "DELETE FROM saves WHERE id=?";

        Connection connection = ConnectionDB.getInstance().getConnection();

        try (connection; PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

            System.out.println("Save deleted!");

        } catch (SQLException e) {
            System.out.println("Error deleting save:");
            System.out.println(e.getMessage());
        }
    }
}