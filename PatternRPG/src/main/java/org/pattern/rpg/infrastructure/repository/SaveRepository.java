package org.pattern.rpg.infrastructure.repository;

import org.pattern.rpg.domain.entity.Player;
import org.pattern.rpg.infrastructure.database.ConnectionDB;
import org.pattern.rpg.domain.item.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SaveRepository {

    public record SaveData(int id, String name, int hp, int attack, int defense, double crit, int floor, String weapon, String armor, String items) {}

    public void initDatabase() {
        String checkSql = "PRAGMA table_info(saves);";
        boolean needsReset = false;
        boolean hasItems = false;
        boolean hasArmor = false;
        
        try (Connection conn = ConnectionDB.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(checkSql)) {
            
            boolean hasName = false;
            while (rs.next()) {
                String columnName = rs.getString("name");
                if ("name".equals(columnName)) hasName = true;
                if ("armor".equals(columnName)) hasArmor = true;
                if ("items".equals(columnName)) hasItems = true;
            }
            
            if (!hasName && rs.isBeforeFirst()) {
                needsReset = true; 
            }
        } catch (SQLException e) {}

        try (Connection conn = ConnectionDB.getInstance().getConnection();
             Statement stmt = conn.createStatement()) {
            
            if (needsReset) {
                stmt.execute("DROP TABLE saves;");
            }

            String sql = """
                CREATE TABLE IF NOT EXISTS saves (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    hp INTEGER,
                    attack INTEGER,
                    defense INTEGER,
                    crit REAL,
                    floor INTEGER,
                    weapon TEXT,
                    armor TEXT DEFAULT 'Nenhuma',
                    items TEXT DEFAULT ''
                );
            """;
            stmt.execute(sql);

            if (!needsReset && !hasArmor) {
                try { stmt.execute("ALTER TABLE saves ADD COLUMN armor TEXT DEFAULT 'Nenhuma';"); } catch (SQLException ignore) {}
            }
            if (!needsReset && !hasItems) {
                try { stmt.execute("ALTER TABLE saves ADD COLUMN items TEXT DEFAULT '';"); } catch (SQLException ignore) {}
            }

        } catch (SQLException e) {
            System.err.println("Erro ao inicializar banco: " + e.getMessage());
        }
    }

    public void saveGame(Player player, int floor, String weapon, String armor) {
        String sql = """
            INSERT INTO saves (name, hp, attack, defense, crit, floor, weapon, armor, items)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        
        // Converte a lista de itens em uma string separada por vírgulas para o banco
        String itemsStr = "";
        if (player.getInventory() != null) {
            itemsStr = player.getInventory().stream()
                        .map(Item::getName)
                        .collect(Collectors.joining(","));
        }

        Connection conn = ConnectionDB.getInstance().getConnection();
        if (conn == null) return;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, player.getName());
            pstmt.setInt(2, player.getHP());
            pstmt.setInt(3, player.getAttack());
            pstmt.setInt(4, player.getDefense());
            pstmt.setDouble(5, player.getCriticalChance());
            pstmt.setInt(6, floor);
            pstmt.setString(7, weapon);
            pstmt.setString(8, armor);
            pstmt.setString(9, itemsStr);
            pstmt.executeUpdate();
            System.out.println("DEBUG: Jogo salvo com sucesso (incluindo inventário).");
        } catch (SQLException e) {
            System.err.println("Erro ao salvar jogo: " + e.getMessage());
        }
    }

    public List<SaveData> getAllSaves() {
        List<SaveData> saves = new ArrayList<>();
        String sql = "SELECT * FROM saves";
        Connection conn = ConnectionDB.getInstance().getConnection();
        if (conn == null) return saves;
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                saves.add(new SaveData(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("hp"),
                    rs.getInt("attack"),
                    rs.getInt("defense"),
                    rs.getDouble("crit"),
                    rs.getInt("floor"),
                    rs.getString("weapon"),
                    rs.getString("armor"),
                    rs.getString("items")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar saves: " + e.getMessage());
        }
        return saves;
    }

    public List<SaveData> getHighScores() {
        List<SaveData> saves = new ArrayList<>();
        String sql = "SELECT * FROM saves ORDER BY floor DESC LIMIT 10";
        Connection conn = ConnectionDB.getInstance().getConnection();
        if (conn == null) return saves;
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                saves.add(new SaveData(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("hp"),
                    rs.getInt("attack"),
                    rs.getInt("defense"),
                    rs.getDouble("crit"),
                    rs.getInt("floor"),
                    rs.getString("weapon"),
                    rs.getString("armor"),
                    rs.getString("items")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar scores: " + e.getMessage());
        }
        return saves;
    }

    public void deleteSave(int id) {
        String sql = "DELETE FROM saves WHERE id = ?";
        Connection conn = ConnectionDB.getInstance().getConnection();
        if (conn == null) return;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar save: " + e.getMessage());
        }
    }
}
