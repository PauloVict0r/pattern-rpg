package org.pattern.rpg.domain.builder;

import java.util.function.Supplier;
import org.pattern.rpg.domain.entity.enemy.Enemy;
import org.pattern.rpg.domain.weapon_strategy.WeaponStrategy;

public class EnemyBuilder implements CreatureBuilder {
    private Enemy enemy;
    private Supplier<Enemy> enemySupplier;

    // Recebe a instrução de qual inimigo concreto instanciar (ex: Orc::new)
    public EnemyBuilder(Supplier<Enemy> enemySupplier) {
        this.enemySupplier = enemySupplier;
        this.reset();
    }

    @Override
    public void reset() {
        this.enemy = this.enemySupplier.get();
    }

    @Override
    public void setName(String name) { this.enemy.setName(name); }

    @Override
    public void setHP(int hp) { this.enemy.setHP(hp); }

    @Override
    public void setDefense(int defense) { this.enemy.setDefense(defense); }

    @Override
    public void setAttack(int attack) { this.enemy.setAttack(attack); }

    @Override
    public void setCriticalChance(double criticalChance) { this.enemy.setCriticalChance(criticalChance); }

    @Override
    public void setWeapon(WeaponStrategy weapon) { this.enemy.setWeapon(weapon); }
    
    // Retorna o Enemy construído
    public Enemy getResult() {
        Enemy finishedEnemy = this.enemy;
        this.reset(); // Prepara o builder para montar o próximo inimigo
        return finishedEnemy;
    }
}