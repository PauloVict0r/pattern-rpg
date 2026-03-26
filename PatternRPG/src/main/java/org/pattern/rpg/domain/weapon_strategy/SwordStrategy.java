package org.pattern.rpg.domain.weapon_strategy;

import org.pattern.rpg.domain.entity.Entity;
import org.pattern.rpg.domain.item.Item;

public class SwordStrategy extends Weapon {

    @Override
    public int attack(Entity target, int damageStat, double critStat) {
        int damage = this.weaponDamage() + damageStat;

        if (Math.random() < critStat) {
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
<<<<<<< HEAD
}
=======
}
>>>>>>> 4543bc425957374648bc203e70ece873fc2b3385
