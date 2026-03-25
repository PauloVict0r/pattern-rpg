package org.pattern.rpg.domain.weapon_strategy;

import org.pattern.rpg.domain.entity.Entity;
import org.pattern.rpg.domain.item.Item;

public class SwordStrategy extends Weapon {

    @Override
    public int attack(Entity target) {
        int damage = this.weaponDamage();

        if (Math.random() < target.getCriticalChance()) {
            damage *= 2;
        }

        return target.receiveDamage(damage);
    }

    @Override
    public int weaponDamage() {
        return 5;
    }

    @Override
    public String getName() {
        return "Espada comum";
    }

    @Override
    public String getDescription() {
        return "Uma espada feita de um metal ordinário.";
    }
}
