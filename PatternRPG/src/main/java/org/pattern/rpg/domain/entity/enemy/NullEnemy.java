package org.pattern.rpg.domain.entity.enemy;

public class NullEnemy extends Enemy {
    public NullEnemy() {
        initStats("Inimigo Inexistente", 0, 0);
    }

    @Override
    public void attack() {
        System.out.println("Uma sombra se dissipa... Nada acontece.");
    }
}
