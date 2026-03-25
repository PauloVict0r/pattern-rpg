package org.pattern.rpg.domain.battle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.pattern.rpg.domain.entity.enemy.Enemy;
import org.pattern.rpg.domain.factory.*;
import org.pattern.rpg.domain.entity.*;
import org.pattern.rpg.presentation.menu.Menu;
import org.pattern.rpg.presentation.ui.ConsoleUI;


public class TurnBattle extends Battle {
    
    // lista de inimigos e player
    private List<Enemy> enemies;
    private Player player;
    private String logBatalha;
    private ConsoleUI ui;
    private Scanner scanner;
    
    // fila de turnos
    private List<Creature> turnQueue;
    private int currentTurnIndex;
    
    // construtor
    public TurnBattle(Player player, Scanner scanner, ConsoleUI ui) {
        this.scanner = scanner;
        this.ui = ui;
        this.player = player;
    }

    @Override
    protected List<Enemy> createEnemies() {
        List<String> enumEnemies = List.of("goblin", "wolf", "skeleton", "vampire", "dragon", "hollow");
        List<Enemy> spawnedEnemies = new ArrayList<>();
        int numEnemies = new Random().nextInt(3) + 1; // 1 a 3 inimigos

        // sorteia tipos de inimigos
        for (int i = 0; i < numEnemies; i++) {
            String type = enumEnemies.get(new Random().nextInt(enumEnemies.size()));
            spawnedEnemies.add(EnemyFactory.createEnemy(type));
        }

        return spawnedEnemies;
    }

    @Override
    protected void setup() {
        this.battleState = 0; // Em andamento
        this.enemies = createEnemies();

        // cria fila de turnos com player e inimigos
        this.turnQueue = new ArrayList<>();
        this.turnQueue.add(player);
        this.turnQueue.addAll(enemies);
        this.currentTurnIndex = 0;

        logBatalha = "Batalha iniciada!";
    }

    @Override
    protected Creature nextTurn() {
        // pega o próximo na fila de turnos
        Creature current = turnQueue.get(currentTurnIndex);
        currentTurnIndex = (currentTurnIndex + 1) % turnQueue.size();

        // se a entidade estiver morta, pula para o próximo
        while (!current.isAlive()) {
            current = turnQueue.get(currentTurnIndex);
            currentTurnIndex = (currentTurnIndex + 1) % turnQueue.size();
        }

        return current;
    }

    @Override
    protected void executeTurn(Creature creature) {
        if (creature instanceof Player) {
            logBatalha = "Seu turno! Escolha sua ação";
            Menu.Batalha(player, enemies, logBatalha, ui);
            // lógica player (por fazer)
        } else {
            logBatalha = "Turno do Inimigo: " + creature.getName();
            //creature.attack();
        }
    }

    @Override
    protected boolean isOver() {
        // checa se o jogador morreu
        if (!player.isAlive()) {
            this.battleState = 2; // derrota
            return true;
        }

        // checa se todos os inimigos morreram
        boolean allEnemiesDead = true;
        for (Enemy e : enemies) {
            if (e.isAlive()) {
                allEnemiesDead = false;
                break;
            }
        }

        if (allEnemiesDead) {
            this.battleState = 1; // vitória
            return true;
        }

        return false;
    }

    @Override
    protected void finish() {
        if (battleState == 1) {
            logBatalha = "Vitória";
        } else if (battleState == 2) {
            logBatalha = "Derrota";
        }
    }
}
