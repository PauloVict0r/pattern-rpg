package org.pattern.rpg.domain.weapon_strategy;

import org.pattern.rpg.domain.entity.Entity;

public class StrongBowStrategy extends Weapon {

    @Override
    public int attack(Entity target) {
        int damage = this.weaponDamage();

        if (Math.random() < target.getCriticalChance()) {
            damage *= 3;
        }

        return target.receiveDamage(damage);
    }

    @Override
    public int weaponDamage() {
        return 8;
    }

    @Override
    public String getName() {
        return "Arco Reforçado";
    }

    @Override
    public String getDescription() {
        return "Um arco resistente que dispara flechas com maior força e precisão.";
    }
}
