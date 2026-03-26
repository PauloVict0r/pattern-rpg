package org.pattern.rpg.domain.entity;

public class Player extends Creature {

    public Player() {
        // Inicializado vazio para o Builder configurar
    }

    // FUTURAMENTE: Stats virão de PlayerBuilder com progressão de nível.
    public Player(String name) {
        setName(name);
        setHp(100);
        setAttack(15);
        setDefense(3);
    }

    public Player(String name, int hp, int attack, int defense, double crit) {
        setName(name);
        setHp(hp);
        setAttack(attack);
        setDefense(defense);
        setCriticalChance(crit);
    }
}