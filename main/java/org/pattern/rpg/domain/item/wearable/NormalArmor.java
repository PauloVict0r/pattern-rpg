package org.pattern.rpg.domain.item.wearable;

import org.pattern.rpg.domain.entity.Entity;
import org.pattern.rpg.domain.stats.StatsBonus;

public class NormalArmor extends WearableDecorator {

    public NormalArmor(Entity wrappedEntity) {
        super(wrappedEntity, new StatsBonus(0, 5, 10, 0));
    }

    @Override
    public String getName() {
        return "Armadura Normal";
    }

    @Override
    public String getDescription() {
        return "Uma armadura Normal. Oferece uma proteção normal contra ataques além de aumentar a vitalidade do usuário.";
    }

}
