package org.pattern.rpg.domain.weapon_strategy;

import org.pattern.rpg.domain.entity.Entity;

public class AxeStrategy extends Weapon {

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
        return 10;
    }

    @Override
    public String getName() {
        return "Machado Pesado";
    }

    @Override
    public String getDescription() {
        return "Uma arma brutal que causa grande dano, porém exige força para ser utilizada.";
    }
}