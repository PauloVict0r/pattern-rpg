package org.pattern.rpg.application;
import java.util.ArrayList;
import java.util.List;

import org.pattern.rpg.domain.battle.*;
import org.pattern.rpg.domain.builder.PlayerBuilder;
import org.pattern.rpg.domain.entity.Entity;
import org.pattern.rpg.domain.entity.Player;
import org.pattern.rpg.domain.entity.Save;
import org.pattern.rpg.infrastructure.database.SaveRepository;
import org.pattern.rpg.presentation.menu.Menu;
import org.pattern.rpg.presentation.ui.ConsoleUI;

import java.util.Scanner;

public class GameManager {

    private Scanner scanner;
    private ConsoleUI ui;
    private Menu menu;
    private InventoryManager inventoryManager;
    private SaveRepository saveRepository;
    private boolean isRunning;
    private Entity player;
    private TurnBattle currentBattle;

    private int currentFloor;
    private int currentScore;
    private String[] currentItems;

    public GameManager() {
        scanner = new Scanner(System.in);
        ui = new ConsoleUI(scanner);
        menu = new Menu(ui, this);
        inventoryManager = new InventoryManager(ui);
        saveRepository = new SaveRepository();

        currentFloor = 1;
        currentScore = 0;
        List<String> currentItems = new ArrayList<>();
    }

    public void startApplication() {
        isRunning = true;
        while (isRunning) {
            menu.showMainMenu();
        }
    }

    public void stopGame() {
        isRunning = false;
    }

    public void startNewGame(String name, String equipment) {

        currentFloor = 1;
        currentScore = 100;

        // Instanciar novo player usando PlayerBuilder
        PlayerBuilder playerBuilder = new PlayerBuilder(Player::new);
        playerBuilder.setName(name);
        playerBuilder.setHP(100);
        playerBuilder.setAttack(15);
        playerBuilder.setDefense(10);
        playerBuilder.setCriticalChance(0.1);
        
        this.player = playerBuilder.getResult();
        this.currentBattle = new TurnBattle(player, ui);
        currentBattle.startBattle();



        menu.showGameOver(name, equipment);
    }

    public void continueGame() {
        // inventoryManager.showInventory(100, currentScore, "Player");
    }

    // SAVE

    public List<Save> listSaves() {
        return saveRepository.listAllSaves();
    }

    public Save loadSave(int id) {
        return saveRepository.loadSave(id);
    }

    public void applyLoadedSave(Save save) {
        if (save == null) return;

        currentFloor = save.getFloor();
        currentScore = save.getScore();
        currentItems = save.getItems();
    }

    public void createSave() {
        saveRepository.createSave(new Save(currentItems, currentFloor, currentScore));
    }

    public void overwriteSave(int id) {
        saveRepository.overwriteSave(new Save(id, currentItems, currentFloor, currentScore));
    }

    public void deleteSave(int id) {
        saveRepository.deleteSave(id);
    }

    public int getCurrentFloor() { return currentFloor; }
    public int getCurrentScore() { return currentScore; }
}