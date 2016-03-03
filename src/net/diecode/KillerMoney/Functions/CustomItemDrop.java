package net.diecode.KillerMoney.Functions;

import net.diecode.KillerMoney.CustomEvents.KillerMoneyCustomItemDropEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class CustomItemDrop implements Listener {

    @EventHandler (priority = EventPriority.MONITOR)
    public void onCustomItemDrop(KillerMoneyCustomItemDropEvent event) {

        if (event.isCancelled()) {
            return;
        }

        event.getLocation().getWorld().dropItemNaturally(event.getLocation(), event.getItem());
    }

}
