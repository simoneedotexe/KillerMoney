package net.diecode.KillerMoney.CustomEvents;

import net.diecode.KillerMoney.CustomObjects.CustomItems;
import net.diecode.KillerMoney.CustomObjects.Mobs;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class KillerMoneyCustomItemDropEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private Player player;
    private Mobs entityConfig;
    private CustomItems customItemConfig;
    private LivingEntity entity;
    private ItemStack item;
    private Location location;
    private boolean cancelled;

    public KillerMoneyCustomItemDropEvent(Player player, Mobs entityConfig, CustomItems customItemConfig,
                                          LivingEntity entity, ItemStack item, Location location) {
        this.player = player;
        this.entityConfig = entityConfig;
        this.customItemConfig = customItemConfig;
        this.entity = entity;
        this.item = item;
        this.location = location;
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

    public CustomItems getCustomItemConfig() {
        return customItemConfig;
    }

    public LivingEntity getEntity() {
        return entity;
    }

    public ItemStack getItem() {
        return item;
    }

    public Location getLocation() {
        return location;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
