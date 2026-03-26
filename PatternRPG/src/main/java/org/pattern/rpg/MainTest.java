package org.pattern.rpg;

import org.pattern.rpg.domain.entity.Player;
import org.pattern.rpg.infrastructure.repository.SaveRepository;
import java.util.List;

public class MainTest {
    public static void main(String[] args) {
        SaveRepository repo = new SaveRepository();
        repo.initDatabase();
        
        System.out.println("--- TESTE DE BANCO DE DADOS ---");
        
        Player testPlayer = new Player("Teste_" + System.currentTimeMillis());
        repo.saveGame(testPlayer, 1, "Espada de Teste", "Nenhuma");
        
        List<SaveRepository.SaveData> saves = repo.getAllSaves();
        System.out.println("Total de saves encontrados: " + saves.size());
        
        for (SaveRepository.SaveData s : saves) {
            System.out.println("ID: " + s.id() + " | Nome: " + s.name() + " | Andar: " + s.floor());
        }
        
        if (saves.isEmpty()) {
            System.out.println("FALHA: O banco continua vazio mesmo após o save.");
        } else {
            System.out.println("SUCESSO: O banco está persistindo os dados.");
        }
    }
}
