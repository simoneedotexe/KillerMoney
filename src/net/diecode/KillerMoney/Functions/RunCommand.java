package net.diecode.KillerMoney.Functions;

import net.diecode.KillerMoney.CustomEvents.KillerMoneyRunCustomCommandEvent;
import net.diecode.KillerMoney.KillerMoney;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class RunCommand implements Listener {

    @EventHandler (priority = EventPriority.MONITOR)
    public void onRunCommand(KillerMoneyRunCustomCommandEvent event) {

        if (event.isCancelled()) {
            return;
        }

        String currentCommand = event.getCommand();

        if (currentCommand == null) {
            return;
        }

        currentCommand = currentCommand.replace("{player}", event.getPlayer().getName());

        KillerMoney.getInstance().getServer().dispatchCommand(
                KillerMoney.getInstance().getServer().getConsoleSender(), currentCommand
        );


    }

}
