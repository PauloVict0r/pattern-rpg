package org.pattern.rpg.domain.item.wearable;

import org.pattern.rpg.domain.entity.Entity;
import org.pattern.rpg.domain.stats.StatsBonus;

public class IronArmor extends WearableDecorator {

    public IronArmor(Entity wrappedEntity) {
        super(wrappedEntity, new StatsBonus(0, 8, 15, 0));
    }

    @Override
    public String getName() {
        return "Armadura de Ferro";
    }

    @Override
    public String getDescription() {
        return "Uma armadura resistente forjada em ferro, oferecendo boa proteção e aumento moderado de vitalidade.";
    }
}