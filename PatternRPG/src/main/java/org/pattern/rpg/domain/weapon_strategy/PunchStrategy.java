package org.pattern.rpg.domain.weapon_strategy;

import org.pattern.rpg.domain.entity.Entity;

public class PunchStrategy extends Weapon {

    @Override
    public int weaponDamage() {
        return 1;
    }

    @Override
    public String getName() {
        return "Punhos";
    }

    @Override
    public String getDescription() {
        return "Seus punhos";
    }
}