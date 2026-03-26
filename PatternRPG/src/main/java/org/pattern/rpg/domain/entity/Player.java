package org.pattern.rpg.domain.entity;

public class Player extends Creature {

    // FUTURAMENTE: Stats virão de PlayerBuilder com progressão de nível.
    public Player(String name) {
        setName(name);
        setHp(100);
        setAttack(15);
        setDefense(3);
    }
}