package net.diecode.KillerMoney.CustomEvents;

import net.diecode.KillerMoney.CustomObjects.Mobs;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class KillerMoneyRunCustomCommandEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private Player player;
    private Mobs entityConfig;
    private LivingEntity entity;
    private String command;
    private boolean cancelled;

    public KillerMoneyRunCustomCommandEvent(Player player, Mobs entityConfig, LivingEntity entity, String command) {
        this.player = player;
        this.entityConfig = entityConfig;
        this.entity = entity;
        this.command = command;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public Player getPlayer() {
        return player;
    }

    public Mobs getEntityConfig() {
        return entityConfig;
    }

    public LivingEntity getEntity() {
        return entity;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
