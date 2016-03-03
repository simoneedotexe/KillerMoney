package net.diecode.KillerMoney.CustomEvents;

import net.diecode.KillerMoney.CustomObjects.Mobs;
import net.diecode.KillerMoney.Enums.EventSource;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class KillerMoneyCashTransferEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private Player killer;
    private Player victim;
    private int cashTransferPercent;
    private int cashTransferLimit;
    private Mobs entityConfig;
    private EventSource eventSource;
    private boolean cancelled;

    public KillerMoneyCashTransferEvent(Player player, Player victim, int cashTransferPercent, int cashTransferLimit,
                                        EventSource eventSource, Mobs entityConfig) {
        this.killer = player;
        this.victim = victim;
        this.cashTransferPercent = cashTransferPercent;
        this.cashTransferLimit = cashTransferLimit;
        this.eventSource = eventSource;
        this.entityConfig = entityConfig;
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

    public Player getKiller() {
        return killer;
    }

    public Player getVictim() {
        return victim;
    }

    public Mobs getEntityConfig() {
        return entityConfig;
    }

    public int getCashTransferPercent() {
        return cashTransferPercent;
    }

    public int getCashTransferLimit() {
        return cashTransferLimit;
    }

    public void setCashTransferPercent(int cashTransferPercent) {
        this.cashTransferPercent = cashTransferPercent;
    }

    public void setCashTransferLimit(int cashTransferLimit) {
        this.cashTransferLimit = cashTransferLimit;
    }

    public EventSource getEventSource() {
        return eventSource;
    }
}
