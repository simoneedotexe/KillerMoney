package net.diecode.KillerMoney.CustomEvents;

import net.diecode.KillerMoney.CustomObjects.LangMessages;
import net.diecode.KillerMoney.CustomObjects.Mobs;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;

public class KillerMoneySendMessageEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private static String prefix;

    private Player player;
    private Mobs entityConfig;
    private LivingEntity entity;
    private ArrayList<String> messages;
    private boolean randomMessage;
    private double money;
    private boolean cancelled;

    static {
        prefix = LangMessages.getPrefix();
    }

    public KillerMoneySendMessageEvent(Player player, Mobs entityConfig, LivingEntity entity, double money) {
        this.player = player;
        this.entityConfig = entityConfig;
        this.entity = entity;
        this.money = money;
    }

    public KillerMoneySendMessageEvent(Player player, Mobs entityConfig, LivingEntity entity, ArrayList<String> messages,
                                       boolean randomMessage, double money) {
        this.player = player;
        this.entityConfig = entityConfig;
        this.entity = entity;
        this.messages = messages;
        this.randomMessage = randomMessage;
        this.money = money;
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

    public static String getPrefix() {
        return prefix;
    }

    public ArrayList<String> getMessages() {
        return messages;
    }

    public static void setPrefix(String prefix) {
        KillerMoneySendMessageEvent.prefix = prefix;
    }

    public void setMessages(ArrayList<String> messages) {
        this.messages = messages;
    }

    public void setRandomMessage(boolean randomMessage) {
        this.randomMessage = randomMessage;
    }

    public boolean isRandomMessage() {
        return randomMessage;
    }

    public double getMoney() {
        return money;
    }
}
