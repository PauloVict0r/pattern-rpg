package org.pattern.rpg.domain.weapon_strategy;

import org.pattern.rpg.domain.entity.Entity;

public interface WeaponStrategy {

    int attack(Entity target, int damageStat, double critStat);
    int weaponDamage();
}