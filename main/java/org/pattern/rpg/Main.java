package org.pattern.rpg;

import org.pattern.rpg.application.GameManager;

public class Main {
    public static void main(String[] args) {

        // GameManager atua como Facade
        GameManager gameManager = new GameManager();

        gameManager.startApplication();
    }
}