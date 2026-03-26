package org.pattern.rpg.domain.battle;

import org.pattern.rpg.domain.entity.*;
import org.pattern.rpg.domain.entity.enemy.Enemy;

import java.util.List;

public abstract class Battle {

    protected int battleState; //0: em andamento; 1: vitória; 2: derrota

    public final void startBattle() {
        setup();

        while (!isOver()) {
            Entity currentCreature = nextTurn();
            executeTurn(currentCreature);
        }

        finish();
    }

    protected abstract void setup();
    protected abstract Entity nextTurn();
    protected abstract void executeTurn(Entity creature);
    protected abstract boolean isOver();
    protected abstract void finish();
    protected abstract List<Enemy> createEnemies();
}
