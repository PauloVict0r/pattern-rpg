package org.pattern.rpg.domain.builder;

import org.pattern.rpg.domain.weapon_strategy.WeaponStrategy;

public interface CreatureBuilder {
    void reset();
    void setName(String name);
    void setHP(int hp);
    void setDefense(int defense);
    void setAttack(int attack);
    void setCriticalChance(double criticalChance);
    void setWeapon(WeaponStrategy weapon);

    String getName();
    int getHP();
    int getDefense();
    int getAttack();
    double getCriticalChance();
}
