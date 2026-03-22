package org.pattern.rpg.domain.battle;

import java.util.List;

public abstract class Battle {

    protected int battleState; //0: em andamento; 1: vitória; 2: derrota

    //TEMPLATE METHOD
    public final void startBattle() {
        setup();

        while (!isOver()) {
            Entity currentEntity = nextTurn();
            executeTurn(currentEntity);
        }

        finish();
    }

    protected abstract void setup();
    protected abstract Entity nextTurn();
    protected abstract void executeTurn(Entity entity);
    protected abstract boolean isOver();
    protected abstract void finish();
    protected abstract List<Enemy> createEnemies();
}
