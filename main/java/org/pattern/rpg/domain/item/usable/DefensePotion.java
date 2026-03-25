package org.pattern.rpg.domain.item.usable;

public class DefensePotion extends Usable {
    private final int defenseIncrease = 15;

    @Override
    public String getName() {
        return "Poção de Defesa";
    }

    @Override
    public String getDescription() {
        return "Aumenta temporariamente a defesa física.";
    }

    public int getDefenseIncrease() {
        return defenseIncrease;
    }
}
