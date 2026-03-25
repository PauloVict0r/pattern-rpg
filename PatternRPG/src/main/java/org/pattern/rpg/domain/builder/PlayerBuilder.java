package org.pattern.rpg.domain.builder;

import java.util.List;
import java.util.function.Supplier;
import org.pattern.rpg.domain.item.Item;
import org.pattern.rpg.domain.entity.Player;
import org.pattern.rpg.domain.weapon_strategy.WeaponStrategy;

public class PlayerBuilder implements CreatureBuilder {
    private Player player;
    private Supplier<Player> playerSupplier;

    // Construtor recebe como instanciar o Player (ex: Player::new)
    public PlayerBuilder(Supplier<Player> playerSupplier) {
        this.playerSupplier = playerSupplier;
        this.reset();
    }

    @Override
    public void reset() {
        this.player = this.playerSupplier.get();
    }

    @Override
    public void setName(String name) { this.player.setName(name); }

    @Override
    public void setHP(int hp) { this.player.setHP(hp); }

    @Override
    public void setDefense(int defense) { this.player.setDefense(defense); }

    @Override
    public void setAttack(int attack) { this.player.setAttack(attack); }

    @Override
    public void setCriticalChance(double criticalChance) { this.player.setCriticalChance(criticalChance); }

    @Override
    public void setWeapon(WeaponStrategy weapon) { this.player.setWeapon(weapon); }

    public void setInventory(List<Item> items) { 
        this.player.setInventory(items); 
    }

    // Retorna o Player pronto para jogar
    public Player getResult() {
        Player finishedPlayer = this.player;
        this.reset(); // Zera o builder
        return finishedPlayer;
    }
}