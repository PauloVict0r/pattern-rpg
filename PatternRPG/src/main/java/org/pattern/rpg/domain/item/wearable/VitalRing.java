package org.pattern.rpg.domain.item.wearable;

import org.pattern.rpg.domain.entity.Entity;
import org.pattern.rpg.domain.stats.StatsBonus;

public class VitalRing extends WearableDecorator {

    public VitalRing(Entity wrappedEntity) {
        super(wrappedEntity, new StatsBonus(0, 2, 25, 0));
    }

    @Override
    public String getName() {
        return "Anel da Vitalidade";
    }

    @Override
    public String getDescription() {
        return "Um anel encantado que fortalece o corpo do usuário, aumentando consideravelmente sua vida.";
    }
}