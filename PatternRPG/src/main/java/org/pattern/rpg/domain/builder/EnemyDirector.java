package org.pattern.rpg.domain.builder;

public class EnemyDirector {
    public void makeMinion(CreatureBuilder builder) {
        builder.reset();
        builder.setHP(50);
        builder.setAttack(10);
        builder.setDefence(5);
        builder.setWeapon("Adaga de Madeira");
        builder.setArmor("Trapos");
    }

    public void makeChampion(CreatureBuilder builder) {
        builder.reset();
        builder.setHP(500);
        builder.setAttack(80);
        builder.setDefence(50);
        builder.setWeapon("Espada de Aço");
        builder.setArmor("Cota de Malha");
        builder.setInventory(Arrays.asList("Poção de Cura", "Escudo"));
    }

    public void makeBoss(CreatureBuilder builder) {
        builder.reset();
        builder.setHP(2000);
        builder.setAttack(250);
        builder.setDefence(150);
        builder.setWeapon("Martelo do Trovão");
        builder.setArmor("Armadura de Placas");
    }
}
