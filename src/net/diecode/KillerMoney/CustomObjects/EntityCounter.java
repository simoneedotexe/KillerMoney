package net.diecode.KillerMoney.CustomObjects;

import net.diecode.KillerMoney.DataStore;
import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.UUID;

public class EntityCounter {

    private UUID owner;
    private HashMap<EntityType, Integer> counter = new HashMap<EntityType, Integer>();

    public EntityCounter(UUID owner) {
        this.owner = owner;

        DataStore.getEntityCounterObj().put(owner, this);
    }

    public HashMap<EntityType, Integer> getCounter() {
        return counter;
    }

    public UUID getOwner() {
        return owner;
    }

    public void increase(EntityType et) {
        if (counter.containsKey(et)) {
            int current = counter.get(et);

            counter.put(et, current + 1);
        } else {
            counter.put(et, 1);
        }
    }

    public int get(EntityType et) {
        if (counter.containsKey(et)) {
            return counter.get(et);
        }

        return 0;
    }

}
