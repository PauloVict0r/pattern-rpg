package org.pattern.rpg.domain.entity;

public interface Entity {
    int getAttack();
    int getHP();
    int getDefense();
    double getCriticalChance();
    int attack(Entity target);
    int receiveDamage(int damage);
    boolean isAlive();
    boolean isDead();
    String getName();
}
