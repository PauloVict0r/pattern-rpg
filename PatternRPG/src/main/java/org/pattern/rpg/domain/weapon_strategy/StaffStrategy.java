package org.pattern.rpg.domain.weapon_strategy;

import org.pattern.rpg.domain.entity.Entity;

public class StaffStrategy extends Weapon {

    @Override
    public int attack(Entity target, int damageStat, double critStat) {
        int damage = this.weaponDamage() + damageStat;

        if (Math.random() < critStat) {
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
<<<<<<< HEAD
}
=======
}
>>>>>>> 4543bc425957374648bc203e70ece873fc2b3385
