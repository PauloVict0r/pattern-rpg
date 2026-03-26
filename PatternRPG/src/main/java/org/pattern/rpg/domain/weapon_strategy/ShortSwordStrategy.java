package org.pattern.rpg.domain.weapon_strategy;

import org.pattern.rpg.domain.entity.Entity;

public class ShortSwordStrategy extends Weapon {

    @Override
    public int weaponDamage() {
        return 7;
    }

    @Override
    public String getName() {
        return "Espada Curta";
    }

    @Override
    public String getDescription() {
        return "Uma espada simples, leve e fácil de manusear.";
    }
}