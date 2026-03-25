package org.pattern.rpg.domain.weapon_strategy;

import org.pattern.rpg.domain.entity.Entity;

public class StrongBowStrategy extends Weapon {

    @Override
    public int attack(Entity target, int damageStat, double critStat) {
        int damage = this.weaponDamage() + damageStat;

        if (Math.random() < critStat) {
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
