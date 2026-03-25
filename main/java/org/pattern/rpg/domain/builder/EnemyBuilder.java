package org.pattern.rpg.domain.builder;

import org.pattern.rpg.domain.entity.enemy.Enemy;
import java.util.List;

public class EnemyBuilder implements CreatureBuilder {
    private Enemy enemy;

    public EnemyBuilder() {
        this.reset();
    }

    @Override
    public void reset() {
        this.enemy = new Enemy();
    }

    // Supondo que a classe Enemy já tenha os métodos prontos
    @Override
    public void setHP(int hp) {
        enemy.setHp(hp);
    }

    @Override
    public void setDefence(int numberDefence) {
        enemy.setDefence(numberDefence);
    }

    @Override
    public void setAttack(int numberAttack) {
        enemy.setAttack(numberAttack);
    }

    @Override
    public void setArmor(String itemArmor) {
        enemy.setArmor(itemArmor);
    }

    @Override
    public void setWeapon(String itemWeapon) {
        enemy.setWeapon(itemWeapon);
    }

    @Override
    public void setInventory(List<String> items) {
        enemy.setInventory(items);
    }

    public Enemy getResult() {
        Enemy product = this.enemy;
        this.reset();
        return product;
    }
}
