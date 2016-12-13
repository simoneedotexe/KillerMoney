package net.diecode.KillerMoney;

import net.diecode.KillerMoney.CustomObjects.EntityCounter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class DataStore {

    private static HashMap<UUID, EntityCounter> ecObj = new HashMap<UUID, EntityCounter>();

    public static HashMap<UUID, EntityCounter> getEntityCounterObj() {
        return ecObj;
    }

    public static EntityCounter getEntityCounter(Player owner) {
        if (ecObj.containsKey(owner.getUniqueId())) {
            return ecObj.get(owner.getUniqueId());
        }

        return null;
    }

}
