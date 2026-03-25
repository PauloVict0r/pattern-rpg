package org.pattern.rpg.domain.weapon_strategy;

import org.pattern.rpg.domain.entity.Entity;

public class LongSwordStrategy extends Weapon {

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
        return 9;
    }

    @Override
    public String getName() {
        return "Espada Longa";
    }

    @Override
    public String getDescription() {
        return "Uma espada bem equilibrada, eficaz em combate corpo a corpo.";
    }
}