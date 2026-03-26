package org.pattern.rpg.domain.item.usable;

public class MediumHealthPotion extends HealthPotion {
    public MediumHealthPotion() {
        this.healthRestore = 50;
    }

    @Override
    public String getName() {
        return "Poção Média de Vida";
    }

    @Override
    public String getDescription() {
        return "Restaura 50 pontos de vida.";
    }
}
