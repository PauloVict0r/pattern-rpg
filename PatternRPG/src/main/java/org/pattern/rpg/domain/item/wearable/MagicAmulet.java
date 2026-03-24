package org.pattern.rpg.domain.item.wearable;

import org.pattern.rpg.domain.entity.Entity;
import org.pattern.rpg.domain.stats.StatsBonus;

public class MagicAmulet extends WearableDecorator {

    public MagicAmulet(Entity wrappedEntity) {
        super(wrappedEntity, new StatsBonus(10, -20, 0, 0.3));
    }

    @Override
    public String getName() {
        return "Amuleto Mágico";
    }

    @Override
    public String getDescription() {
        return "Um antigo amuleto imbuído com energia mágica desconhecida. Dizem que seus usuários ganham força incomum, mas sucumbem pala fadiga.";
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
