package org.pattern.rpg.domain.weapon_strategy;

import org.pattern.rpg.domain.entity.Entity;

public class StaffStrategy extends Weapon {

    @Override
    public int attack(Entity target) {
        int damage = this.weaponDamage();

        if (Math.random() < target.getCriticalChance()) {
            damage *= 4;
        }

        return target.receiveDamage(damage);
    }

    @Override
    public int weaponDamage() {
        return 6;
    }

    @Override
    public String getName() {
        return "Cajado Arcano";
    }

    @Override
    public String getDescription() {
        return "Um cajado imbuído com energia mágica que amplifica ataques.";
    }
}
