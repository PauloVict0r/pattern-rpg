package org.pattern.rpg.domain.item.wearable;

import org.pattern.rpg.domain.entity.Entity;
import org.pattern.rpg.domain.stats.StatsBonus;

public class DragonArmor extends WearableDecorator {

    public DragonArmor(Entity wrappedEntity) {
        super(wrappedEntity, new StatsBonus(8, 15, 40, 0.10));
    }

    @Override
    public String getName() {
        return "Armadura do Dragão";
    }

    @Override
    public String getDescription() {
        return "Forjada a partir de escamas de dragão, oferece proteção extrema, grande vitalidade e aumenta o poder ofensivo.";
    }
}
