package org.pattern.rpg.domain.item.wearable;

import org.pattern.rpg.domain.entity.Entity;
import org.pattern.rpg.domain.stats.StatsBonus;

public class GoldenArmor extends WearableDecorator {

    public GoldenArmor(Entity wrappedEntity) {
        super(wrappedEntity, new StatsBonus(0, 10, 20, 0.05));
    }

    @Override
    public String getName() {
        return "Armadura Dourada";
    }

    @Override
    public String getDescription() {
        return "Uma armadura forjada em ouro refinado, aumenta a vitalidade e eleva a chance de ataques críticos.";
    }

    @Override
    public boolean isEquipable() {
        return true;
    }

    @Override
    public boolean isUsable() {
        return false;
    }
}