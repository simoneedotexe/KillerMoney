package net.diecode.KillerMoney;

import net.diecode.KillerMoney.Configs.Configs;
import net.diecode.KillerMoney.CustomEvents.*;
import net.diecode.KillerMoney.CustomObjects.*;
import net.diecode.KillerMoney.Enums.EventSource;
import net.diecode.KillerMoney.Enums.MobType;
import net.diecode.KillerMoney.Enums.MoneyType;
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
import java.util.concurrent.ConcurrentHashMap;

public class EntityDeath implements Listener {

    private HashMap<UUID, UUID> lastDamager = new HashMap<UUID, UUID>();
    private static ConcurrentHashMap<MobType, Integer> killedMobTypeCounter = new ConcurrentHashMap<MobType, Integer>();

    public EntityDeath() {
        resetMobTypeCounter();
    }

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

        /**
         * MineChart support -> MOB-KILLS Graph
         */
        if (KillerMoney.isMineChartHooked() && Configs.getEnabledGraphs().contains("MOB-KILLS")) {
            increaseMobTypeCounter(victimType);
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
        if (KillerMoney.getMaHandler() != null && Configs.isDisableFunctionsInMA()) {
            if (KillerMoney.getMaHandler().isPlayerInArena(killer)) {
                return;
            }
        }

        if (mobs.getDailyLimit() > 0) {
            EntityCounter ec = DataStore.getEntityCounter(killer);

            if (ec == null) {
                ec = new EntityCounter(killer.getUniqueId());
            }

            ec.increase(victimType);

            int counter = ec.get(victimType);

            if (counter > mobs.getDailyLimit()) {
                killer.sendMessage(LangMessages.getReachedDailyLimit());

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
         * Money reward
         */
        if (mobs.isRewardMoneyEnabled()) {
            moneyProcessor(mobs, killer, victim, MoneyType.REWARD);
        }

        if (mobs.isLoseMoneyEnabled()) {
            if (victim instanceof Player) {
                moneyProcessor(mobs, (Player) victim, killer, MoneyType.LOSE);
            }
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

    private void moneyProcessor(Mobs mobs, Player p, LivingEntity entity, MoneyType type) {
        double money;

        if (type == MoneyType.REWARD) {
            if (!Utils.chanceGenerator(mobs.getRewardMoneyChance())) {
                return;
            }

            money = Utils.randomNumber(mobs.getRewardMoneyMin(), mobs.getRewardMoneyMax());
            money *= Utils.getMultiplier(p);
            money = Utils.decimalFormating(money);

            // money is positive
            if (money > 0) {
                KillerMoney.getInstance().getServer().getPluginManager().callEvent(
                        new KillerMoneyMoneyRewardEvent(p, mobs, money, EventSource.ENTITY_KILL, entity)
                );
            }

            // money is negative
            if (money < 0) {
                money *= -1;

                KillerMoney.getInstance().getServer().getPluginManager().callEvent(
                        new KillerMoneyMoneyLoseEvent(p, mobs, money, EventSource.ENTITY_KILL, entity)
                );
            }
        } else {
            if (!Utils.chanceGenerator(mobs.getLoseMoneyChance())) {
                return;
            }

            money = Utils.randomNumber(mobs.getLoseMoneyMin(), mobs.getLoseMoneyMax());
            money = Utils.decimalFormating(money);

            if (money < 0) {
                money *= -1;
            }

            KillerMoney.getInstance().getServer().getPluginManager().callEvent(
                    new KillerMoneyMoneyLoseEvent(p, mobs, money, EventSource.ENTITY_KILL, entity)
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

    private void increaseMobTypeCounter(EntityType entity) {
        MobType type = MobType.getType(entity);

        if (type == null) {
            return;
        }

        if (killedMobTypeCounter.containsKey(type)) {
            killedMobTypeCounter.put(type, killedMobTypeCounter.get(type) + 1);
        } else {
            killedMobTypeCounter.put(type, 1);
        }
    }

    public static void resetMobTypeCounter() {
        killedMobTypeCounter.clear();

        for (MobType mt : MobType.values()) {
            killedMobTypeCounter.put(mt, 0);
        }
    }

    public static ConcurrentHashMap<MobType, Integer> getKilledMobTypeCounter() {
        return killedMobTypeCounter;
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
