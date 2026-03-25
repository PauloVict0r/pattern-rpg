package org.pattern.rpg;

import org.pattern.rpg.application.GameManager;

public class Main {
    public static void main(String[] args) {
        // O GameManager atua como o nosso Facade.
        GameManager gameManager = new GameManager();
        gameManager.iniciarAplicacao();
    }
}