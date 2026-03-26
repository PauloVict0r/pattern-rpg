package org.pattern.rpg.infrastructure.repository;

import org.pattern.rpg.domain.entity.Player;
import org.pattern.rpg.infrastructure.database.ConnectionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SaveRepository {

    public record SaveData(int id, String name, int hp, int attack, int defense, double crit, int floor, String weapon) {}

    public void initDatabase() {
        // Drop table if it doesn't have the new structure (heuristic)
        // Or just ensure the correct one exists. 
        // To be safe and clean, let's create the correct one.
        
        String checkSql = "PRAGMA table_info(saves);";
        boolean needsReset = false;
        
        try (Connection conn = ConnectionDB.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(checkSql)) {
            
            boolean hasName = false;
            while (rs.next()) {
                if ("name".equals(rs.getString("name"))) {
                    hasName = true;
                }
            }
            
            // If the table exists but doesn't have 'name', it's the old structure from ConnectionDB.
            if (!hasName) {
                // Check if it's the old one (item, pfloor, score)
                needsReset = true; 
            }
            
        } catch (SQLException e) {
            // Table might not exist yet, that's fine.
        }

        if (needsReset) {
            try (Connection conn = ConnectionDB.getInstance().getConnection();
                 Statement stmt = conn.createStatement()) {
                stmt.execute("DROP TABLE saves;");
                System.out.println("DEBUG: Tabela antiga detectada e removida.");
            } catch (SQLException e) {
                System.err.println("Erro ao resetar tabela: " + e.getMessage());
            }
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
                weapon TEXT
            );
        """;
        try (Connection conn = ConnectionDB.getInstance().getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("DEBUG: Tabela 'saves' (nova) inicializada com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao inicializar banco: " + e.getMessage());
        }
    }

    public void saveGame(Player player, int floor, String weapon) {
        String sql = """
            INSERT INTO saves (name, hp, attack, defense, crit, floor, weapon)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;
        Connection conn = ConnectionDB.getInstance().getConnection();
        if (conn == null) {
            System.err.println("Erro: Não foi possível obter conexão com o banco para salvar.");
            return;
        }
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, player.getName());
            pstmt.setInt(2, player.getHP());
            pstmt.setInt(3, player.getAttack());
            pstmt.setInt(4, player.getDefense());
            pstmt.setDouble(5, player.getCriticalChance());
            pstmt.setInt(6, floor);
            pstmt.setString(7, weapon);
            pstmt.executeUpdate();
            System.out.println("DEBUG: Jogo salvo com sucesso no banco de dados.");
        } catch (SQLException e) {
            System.err.println("Erro ao salvar jogo: " + e.getMessage());
        }
    }

    public List<SaveData> getAllSaves() {
        List<SaveData> saves = new ArrayList<>();
        String sql = "SELECT * FROM saves";
        Connection conn = ConnectionDB.getInstance().getConnection();
        if (conn == null) {
            System.err.println("Erro: Não foi possível obter conexão com o banco para listar saves.");
            return saves;
        }
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
                    rs.getString("weapon")
                ));
            }
            System.out.println("DEBUG: Encontrados " + saves.size() + " saves no banco.");
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
                    rs.getString("weapon")
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
        if (conn == null) {
            System.err.println("Erro: Não foi possível obter conexão com o banco para deletar.");
            return;
        }
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("DEBUG: Save com ID " + id + " deletado com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao deletar save: " + e.getMessage());
        }
    }
}
