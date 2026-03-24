package org.pattern.rpg.domain.item.wearable;

import org.pattern.rpg.domain.entity.Entity;
import org.pattern.rpg.domain.item.Item;
import org.pattern.rpg.domain.stats.StatsBonus;

public abstract class WearableDecorator implements Entity, Item {
    private final Entity wrappedEntity;
    private final StatsBonus bonus;

    public WearableDecorator(Entity wrappedEntity,  StatsBonus bonus) {
        this.wrappedEntity = wrappedEntity;
        this.bonus = bonus;
    }

    public StatsBonus getBonus() {
        return bonus;
    }

    @Override
    public int getAttack() {
        return wrappedEntity.getAttack() + bonus.attack();
    }

    @Override
    public int getDefense() {
        return wrappedEntity.getDefense() + bonus.defense();
    }

    @Override
    public int getHP() {
        return wrappedEntity.getHP() + bonus.hp();
    }

    @Override
    public double getCriticalChance() {
        return wrappedEntity.getCriticalChance() + bonus.critChance();
    }

    public int attack(Entity target) {
        return wrappedEntity.attack(target);
    }

    public int receiveDamage(int damage) {
        return wrappedEntity.receiveDamage(damage);
    }

}
