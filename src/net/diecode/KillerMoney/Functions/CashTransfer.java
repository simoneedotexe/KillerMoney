package net.diecode.KillerMoney.Functions;

import net.diecode.KillerMoney.CustomEvents.KillerMoneyCashTransferEvent;
import net.diecode.KillerMoney.CustomEvents.KillerMoneyMoneyLossEvent;
import net.diecode.KillerMoney.CustomEvents.KillerMoneyMoneyRewardEvent;
import net.diecode.KillerMoney.Enums.EventSource;
import net.diecode.KillerMoney.KillerMoney;
import net.diecode.KillerMoney.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class CashTransfer implements Listener {

    @EventHandler (priority = EventPriority.MONITOR)
    public void onCashTransfer(KillerMoneyCashTransferEvent event) {

        if (event.isCancelled()) {
            return;
        }

        Player killer = event.getKiller();
        Player victim = event.getVictim();

        int percent = event.getCashTransferPercent();
        int limit = event.getCashTransferLimit();

        double victimMoney = (int)KillerMoney.getEconomy().getBalance(victim);

        double transferingMoney = (victimMoney / 100) * percent;

        transferingMoney = Utils.decimalFormating(transferingMoney);

        if (limit != 0 && limit < transferingMoney) {
            transferingMoney = limit;
        }

        KillerMoney.getInstance().getServer().getPluginManager().callEvent(
                new KillerMoneyMoneyRewardEvent(
                        killer, event.getEntityConfig(), transferingMoney, EventSource.CASH_TRANSFER, victim
                )
        );

        KillerMoney.getInstance().getServer().getPluginManager().callEvent(
                new KillerMoneyMoneyLossEvent(
                        victim, event.getEntityConfig(), transferingMoney, EventSource.CASH_TRANSFER, killer
                )
        );

    }

}
