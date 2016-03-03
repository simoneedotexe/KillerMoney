package net.diecode.KillerMoney.Functions;

import net.diecode.KillerMoney.Configs.Configs;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import java.util.ArrayList;
import java.util.UUID;

public class Farming implements Listener {

    private static ArrayList<UUID> spawnedMobs = new ArrayList<UUID>();

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

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event) {
        if (event.isCancelled()) {
            return;
        }

        for (Entity currentEntity : event.getChunk().getEntities()) {
            if (spawnedMobs.contains(currentEntity.getUniqueId())) {
                spawnedMobs.remove(currentEntity.getUniqueId());
            }
        }
    }

}
