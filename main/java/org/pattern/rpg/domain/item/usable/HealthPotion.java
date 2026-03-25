package org.pattern.rpg.domain.item.usable;

public class HealthPotion extends Usable {
    private final int healthRestore = 50;

    @Override
    public String getName() {
        return "Poção de Vida";
    }

    @Override
    public String getDescription() {
        return "Restaura uma pequena quantidade de vida.";
    }

    public int getHealthRestore() {
        return healthRestore;
    }
}
