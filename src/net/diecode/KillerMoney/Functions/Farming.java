package net.diecode.KillerMoney.Functions;

import net.diecode.KillerMoney.Configs.Configs;
import net.diecode.KillerMoney.DataStore;
import net.diecode.KillerMoney.KillerMoney;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.ArrayList;
import java.util.UUID;

public class Farming implements Listener {

    private static ArrayList<UUID> spawnedMobs = new ArrayList<UUID>();

    public Farming() {
        Bukkit.getScheduler().runTaskTimer(KillerMoney.getInstance(), new Runnable() {
            @Override
            public void run() {
                DataStore.getEntityCounterObj().clear();
            }
        }, 20L * 60 * 60 * 24, 20L * 60 * 60 * 24);
    }

    public static boolean containsInList(UUID uuid) {
        return spawnedMobs.contains(uuid);
    }

    public static void removeFromList(UUID uuid) {
        if (spawnedMobs.contains(uuid)) {
            spawnedMobs.remove(uuid);
        }
    }

    public static ArrayList<UUID> getSpawnedMobs() {
        return spawnedMobs;
    }

    public static void setSpawnedMobs(ArrayList<UUID> spawnedMobs) {
        Farming.spawnedMobs = spawnedMobs;
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER) {
            if (Configs.isDisableSpawnerFarming()) {
                spawnedMobs.add(event.getEntity().getUniqueId());
                return;
            }
        }

        if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG) {
            if (Configs.isDisableEggFarming()) {
                spawnedMobs.add(event.getEntity().getUniqueId());
                return;
            }
        }
    }

}
