package org.pattern.rpg.domain.entity.enemy;

public class NullEnemy extends Enemy {
    public NullEnemy() {
        this.name = "Inimigo Inexistente";
        this.health = 0;
        this.damage = 0;
    }

    @Override
    public void attack() {
        System.out.println("Uma sombra se dissipa... Nada acontece.");
    }
}
