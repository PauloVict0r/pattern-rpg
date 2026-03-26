package org.pattern.rpg.domain.weapon_strategy;

import org.pattern.rpg.domain.entity.Entity;

public class DragonBladeStrategy extends Weapon {

    @Override
    public int attack(Entity target, int damageStat, double critStat) {
        int damage = this.weaponDamage() +  damageStat;

        if (Math.random() < critStat) {
            damage *= 4;
        }

        return target.receiveDamage(damage);
    }

    @Override
    public int weaponDamage() {
        return 15;
    }

    @Override
    public String getName() {
        return "Lâmina do Dragão";
    }

    @Override
    public String getDescription() {
        return "Uma espada lendária forjada com o poder dos dragões, capaz de causar dano devastador.";
    }
}