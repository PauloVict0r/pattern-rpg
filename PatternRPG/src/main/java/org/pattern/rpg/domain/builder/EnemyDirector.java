package org.pattern.rpg.domain.builder;

import org.pattern.rpg.domain.weapon_strategy.DragonBladeStrategy;
import org.pattern.rpg.domain.weapon_strategy.LongSwordStrategy;
import org.pattern.rpg.domain.weapon_strategy.ShortSwordStrategy;

public class EnemyDirector {
    public void makeMinion(CreatureBuilder builder) {
        builder.reset();
        builder.setHP(50);
        builder.setAttack(10);
        builder.setDefense(5);
        builder.setWeapon(new ShortSwordStrategy());
    }

    public void makeChampion(CreatureBuilder builder) {
        builder.reset();
        builder.setHP(500);
        builder.setAttack(80);
        builder.setDefense(50);
        builder.setWeapon(new LongSwordStrategy());
    }

    public void makeBoss(CreatureBuilder builder) {
        builder.reset();
        builder.setHP(2000);
        builder.setAttack(250);
        builder.setDefense(150);
        builder.setWeapon(new DragonBladeStrategy());
    }
}
