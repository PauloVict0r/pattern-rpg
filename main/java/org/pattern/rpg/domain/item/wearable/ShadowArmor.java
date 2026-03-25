package org.pattern.rpg.domain.item.wearable;

import org.pattern.rpg.domain.entity.Entity;
import org.pattern.rpg.domain.stats.StatsBonus;

public class ShadowArmor extends WearableDecorator {

    public ShadowArmor(Entity wrappedEntity) {
        super(wrappedEntity, new StatsBonus(5, 6, 10, 0.08));
    }

    @Override
    public String getName() {
        return "Armadura Sombria";
    }

    @Override
    public String getDescription() {
        return "Imbuída com energia das sombras, esta armadura equilibra ataque e defesa enquanto aumenta a chance de golpes críticos.";
    }
}