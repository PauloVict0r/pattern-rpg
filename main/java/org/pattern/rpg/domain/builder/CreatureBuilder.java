package org.pattern.rpg.domain.builder;

import java.util.List;

public interface CreatureBuilder {
    void reset();
    void setHP(int numberHp);
    void setDefence(int numberDefence);
    void setAttack(int numberAttack);
    void setArmor(String itemArmor);
    void setWeapon(String itemWeapon);
    void setInventory(List<String> items);
}
