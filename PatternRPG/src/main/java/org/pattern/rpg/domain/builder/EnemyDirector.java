package org.pattern.rpg.domain.builder;

import org.pattern.rpg.domain.weapon_strategy.DragonBladeStrategy;
import org.pattern.rpg.domain.weapon_strategy.LongSwordStrategy;
import org.pattern.rpg.domain.weapon_strategy.ShortSwordStrategy;

public class EnemyDirector {
    public void makeMinion(CreatureBuilder builder) {
        applyModifier(builder, "(Lacaio)", 0.8, new ShortSwordStrategy());
    }

    public void makeChampion(CreatureBuilder builder) {
        applyModifier(builder, "(Campeão)", 1.5, new LongSwordStrategy());
    }

    public void makeBoss(CreatureBuilder builder) {
        applyModifier(builder, "(Chefe)", 3.0, new DragonBladeStrategy());
<<<<<<< HEAD
    }

    private void applyModifier(CreatureBuilder builder, String suffix, double multiplier, org.pattern.rpg.domain.weapon_strategy.WeaponStrategy weapon) {
        builder.reset();
        builder.setName(builder.getName() + " " + suffix);
        builder.setHP((int)(builder.getHP() * multiplier));
        builder.setAttack((int)(builder.getAttack() * multiplier));
        builder.setDefense((int)(builder.getDefense() * multiplier));
        builder.setWeapon(weapon);
=======
>>>>>>> 4543bc425957374648bc203e70ece873fc2b3385
    }

    private void applyModifier(CreatureBuilder builder, String suffix, double multiplier, org.pattern.rpg.domain.weapon_strategy.WeaponStrategy weapon) {
        builder.reset();
        builder.setName(builder.getName() + " " + suffix);
        builder.setHP((int)(builder.getHP() * multiplier));
        builder.setAttack((int)(builder.getAttack() * multiplier));
        builder.setDefense((int)(builder.getDefense() * multiplier));
        builder.setWeapon(weapon);
    }
}