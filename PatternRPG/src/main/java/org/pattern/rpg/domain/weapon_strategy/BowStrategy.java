package org.pattern.rpg.domain.weapon_strategy;

import org.pattern.rpg.domain.entity.Entity;

public class BowStrategy implements WeaponStrategy {

    @Override
    public int attack(Entity target) {
        return target.receiveDamage(this.weaponDamage());
    }

    @Override
    public int weaponDamage() {
        return 5;
    }
}
