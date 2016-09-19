package net.diecode.KillerMoney;

import net.diecode.KillerMoney.Configs.Configs;
import net.diecode.KillerMoney.CustomEvents.*;
import net.diecode.KillerMoney.CustomObjects.CustomCommand;
import net.diecode.KillerMoney.CustomObjects.CustomItems;
import net.diecode.KillerMoney.CustomObjects.Mobs;
import net.diecode.KillerMoney.Enums.EventSource;
import net.diecode.KillerMoney.Functions.Farming;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashMap;
import java.util.UUID;

public class EntityDeath implements Listener {

    private HashMap<UUID, UUID> lastDamager = new HashMap<UUID, UUID>();

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        LivingEntity victim = event.getEntity();
        EntityType victimType = victim.getType();
        Mobs mobs = Mobs.getMobsFromList(victimType);
        Player victimPlayer = null;

        if (victimType == EntityType.PLAYER) {
            victimPlayer = (Player)victim;
        }

        /**
         * Anti farming check
         */
        if (Farming.containsInList(victim.getUniqueId())) {
            Farming.removeFromList(victim.getUniqueId());
            return;
        }

        if (mobs == null) {
            return;
        }

        if (killer == null) {
            if (event.getEntity().getLastDamageCause() != null &&
                    event.getEntity().getLastDamageCause().getCause() != EntityDamageEvent.DamageCause.VOID) {
                return;
            }

            if (victimPlayer == null) {
                return;
            }

            if (!lastDamager.containsKey(victimPlayer.getUniqueId())) {
                return;
            }

            killer = Bukkit.getServer().getPlayer(lastDamager.get(victimPlayer.getUniqueId()));

            lastDamager.remove(victim.getUniqueId());
        }

        if (killer == null) {
            return;
        }

        if (Configs.getGlobalDisabledWorlds().contains(victim.getLocation().getWorld().getName())) {
            return;
        }

        if (killer.getGameMode() == GameMode.CREATIVE) {
            if (Configs.isDisabledFunctionInCreative()) {
                return;
            }
        }

        if (victimPlayer != null) {
            if (killer.getName().equals(victimPlayer.getName())) {
                return;
            }
        }

        /**
         * Permission check
         */
        if (mobs.getPermission() != null && !mobs.getPermission().equals("")) {
            if (!killer.hasPermission(mobs.getPermission())) {
                return;
            }
        }

        /**
         * World check
         */
        if (mobs.getDisabledOnWorlds().contains(killer.getWorld().getName())) {
            return;
        }

        /**
         * MobArena check
         */
        if (KillerMoney.getMaHandler() != null && !Configs.isProcessConfigInArena()) {
                if (KillerMoney.getMaHandler().isPlayerInArena(killer)) {
                    return;
                }
        }

        /**
         * Cash transfer - victim player entity
         */
        if (victimPlayer != null) {
            cashTransferProcessor(mobs, killer, victimPlayer);
        }

        /**
         * Moneys
         */
        if (mobs.isMoneyEnabled()) {
            moneyProcessor(mobs, killer, victim);
        }


        /**
         * Custom item drops
         */
        if (mobs.isCustomItemDropEnabled()) {
            if (mobs.isClearDefaultItemEnabled()) {
                event.getDrops().clear();
            }

            customItemDropProcessor(mobs, killer, victim);
        }


        /**
         * Custom commands
         */
        if (mobs.isCustomCommandsEnabled()) {
            customCommandProcessor(mobs, killer, victim);
        }

    }

    private void moneyProcessor(Mobs mobs, Player killer, LivingEntity entity) {
        if (!Utils.chanceGenerator(mobs.getMoneyChance())) {
            return;
        }

        double money = Utils.randomNumber(mobs.getMoneyMin(), mobs.getMoneyMax());

        money *= Utils.getMultiplier(killer);

        money = Utils.decimalFormating(money);

        // money is positive
        if (money > 0) {

            KillerMoney.getInstance().getServer().getPluginManager().callEvent(
                    new KillerMoneyMoneyRewardEvent(killer, mobs, money, EventSource.ENTITY_KILL, entity)
            );
        }

        // money is negative
        if (money < 0) {

            money *= -1;

            KillerMoney.getInstance().getServer().getPluginManager().callEvent(
                    new KillerMoneyMoneyLossEvent(killer, mobs, money, EventSource.ENTITY_KILL, entity)
            );
        }
    }

    private void cashTransferProcessor(Mobs mobs, Player killer, Player victim) {
        if (Mobs.getCashTransferPercent() <= 0) {
            return;
        }

        if (!Utils.chanceGenerator(Mobs.getCashTransferChance())) {
            return;
        }

        KillerMoney.getInstance().getServer().getPluginManager().callEvent(
                new KillerMoneyCashTransferEvent(killer, victim, Mobs.getCashTransferPercent(),
                        Mobs.getCashTransferLimit(), EventSource.CASH_TRANSFER, mobs
                )
        );

    }

    private void customItemDropProcessor(Mobs mobs, Player killer, LivingEntity entity) {
        // custom item drop event
        for (CustomItems currentItem : mobs.getItems()) {

            if (!Utils.chanceGenerator(currentItem.getChance())) {
                continue;
            }

            if (currentItem.getPermission() != null && !currentItem.getPermission().equals("")) {
                if (!killer.hasPermission(currentItem.getPermission())) {
                    continue;
                }
            }

            if (currentItem.getDisabledWorlds().contains(killer.getWorld().getName())) {
                continue;
            }

            KillerMoney.getInstance().getServer().getPluginManager().callEvent(
                    new KillerMoneyCustomItemDropEvent(
                            killer, mobs, currentItem, entity, currentItem.getItem(), entity.getLocation()
                    )
            );
        }

    }

    private void customCommandProcessor(Mobs mobs, Player killer, LivingEntity entity) {
        // custom commands event
        for (CustomCommand cc : mobs.getCustomCommands()) {
            if (!Utils.chanceGenerator(cc.getChance())) {
                continue;
            }

            Bukkit.getPluginManager().callEvent(
                    new KillerMoneyRunCustomCommandEvent(killer, mobs, entity, cc.getCommand())
            );
        }

    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager().getType() != EntityType.PLAYER) {
            return;
        }

        if (event.getEntity().getType() != EntityType.PLAYER) {
            return;
        }

        Player damager = (Player)event.getDamager();
        Player victim = (Player)event.getEntity();

        lastDamager.put(victim.getUniqueId(), damager.getUniqueId());
    }

}
