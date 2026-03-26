package org.pattern.rpg.domain.item.usable;

public class SmallHealthPotion extends HealthPotion {
    public SmallHealthPotion() {
        this.healthRestore = 25;
    }

    @Override
    public String getName() {
        return "Poção Pequena de Vida";
    }

    @Override
    public String getDescription() {
        return "Restaura 25 pontos de vida.";
    }
}
