package org.pattern.rpg.domain.item.usable;

public class LargeHealthPotion extends HealthPotion {
    public LargeHealthPotion() {
        this.healthRestore = 100;
    }

    @Override
    public String getName() {
        return "Poção Grande de Vida";
    }

    @Override
    public String getDescription() {
        return "Restaura 100 pontos de vida.";
    }
}