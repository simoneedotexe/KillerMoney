package net.diecode.KillerMoney.CustomEvents;

import net.diecode.KillerMoney.CustomObjects.Mobs;
import net.diecode.KillerMoney.Enums.EventSource;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class KillerMoneyMoneyLossEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private Player player;
    private Mobs entityConfig;
    private double money;
    private LivingEntity entity;
    private EventSource eventSource;
    private boolean cancelled;

    public KillerMoneyMoneyLossEvent(Player player, Mobs entityConfig, double money, EventSource eventSource, LivingEntity entity) {
        this.player = player;
        this.entityConfig = entityConfig;
        this.money = money;
        this.eventSource = eventSource;
        this.entity = entity;
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

    public double getMoney() {
        return money;
    }

    public LivingEntity getEntity() {
        return entity;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public EventSource getEventSource() {
        return eventSource;
    }
}
