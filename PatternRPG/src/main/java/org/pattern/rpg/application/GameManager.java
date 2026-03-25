package org.pattern.rpg.application;

import org.pattern.rpg.domain.entity.Save;
import org.pattern.rpg.infrastructure.database.SaveRepository;
import org.pattern.rpg.presentation.menu.Menu;
import org.pattern.rpg.presentation.ui.ConsoleUI;

import java.util.List;
import java.util.Scanner;

public class GameManager {

    private Scanner scanner;
    private ConsoleUI ui;
    private Menu menu;
    private BattleManager battleManager;
    private InventoryManager inventoryManager;
    private SaveRepository saveRepository;
    private boolean isRunning;

    private int currentFloor;
    private int currentScore;
    private String[] currentItems;

    public GameManager() {
        scanner = new Scanner(System.in);
        ui = new ConsoleUI(scanner);
        menu = new Menu(ui, this);
        battleManager = new BattleManager(ui);
        inventoryManager = new InventoryManager(ui);
        saveRepository = new SaveRepository();

        currentFloor = 1;
        currentScore = 0;
        currentItems = new String[]{"Sword", "Potion"};
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
        currentItems = new String[]{equipment, "Potion"};

        battleManager.startTestBattle(name, equipment);

        currentFloor = 2;
        currentScore = 250;

        menu.showGameOver(name, equipment);
    }

    public void continueGame() {
        inventoryManager.showInventory(100, currentScore, "Player");
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