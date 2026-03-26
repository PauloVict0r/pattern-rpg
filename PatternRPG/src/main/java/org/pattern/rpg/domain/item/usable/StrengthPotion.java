package org.pattern.rpg.domain.item.usable;

import org.pattern.rpg.domain.entity.Creature;

public class StrengthPotion extends Usable {
    private int attackBoost = 5;

    @Override
    public String getName() {
        return "Poção de Força";
    }

    @Override
    public String getDescription() {
        return "Aumenta permanentemente o ataque do usuário em " + attackBoost + ".";
    }

    @Override
    public void use(Creature target) {
        target.setAttack(target.getAttack() + attackBoost);
        System.out.println(target.getName() + " consumiu a " + this.getName() + " e seu ataque subiu!");
    }
}
