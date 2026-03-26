package org.pattern.rpg.domain.weapon_strategy;

import org.pattern.rpg.domain.entity.Entity;

public class DaggerStrategy extends Weapon {

    @Override
    public int attack(Entity target, int damageStat, double critStat) {
        int damage = this.weaponDamage() +  damageStat;

        if (Math.random() < critStat) {
            damage *= 2;
        }

        return target.receiveDamage(damage);
    }

    @Override
    public int weaponDamage() {
        return 4;
    }

    @Override
    public String getName() {
        return "Adaga Rápida";
    }

    @Override
    public String getDescription() {
        return "Pequena e veloz, ideal para ataques rápidos e precisos.";
    }
}