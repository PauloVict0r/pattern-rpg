package org.pattern.rpg.domain.item.usable;

import org.pattern.rpg.domain.entity.Creature;

public class DefensePotion extends Usable {
    private int defenseBoost = 3;

    @Override
    public String getName() {
        return "Poção de Defesa";
    }

    @Override
    public String getDescription() {
        return "Aumenta permanentemente a defesa do usuário em " + defenseBoost + ".";
    }

    @Override
    public void use(Creature target) {
        target.setDefense(target.getDefense() + defenseBoost);
        System.out.println(target.getName() + " consumiu a " + this.getName() + " e sua defesa subiu!");
    }
}
