package net.diecode.KillerMoney.Functions;

import net.diecode.KillerMoney.CustomEvents.KillerMoneyMoneyLoseEvent;
import net.diecode.KillerMoney.CustomEvents.KillerMoneySendMessageEvent;
import net.diecode.KillerMoney.CustomObjects.LangMessages;
import net.diecode.KillerMoney.KillerMoney;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class MoneyLose implements Listener {

    @EventHandler (priority = EventPriority.NORMAL)
    public void onMoneyLose(KillerMoneyMoneyLoseEvent event) {

        System.out.println("1");

        if (event.isCancelled()) {
            return;
        }

        System.out.println("2");

        Player player = event.getPlayer();
        EntityType entityType = event.getEntity().getType();
        double money = event.getMoney();

        System.out.println("3");

        KillerMoney.getEconomy().withdrawPlayer(player, money);

        LangMessages langMessages = LangMessages.getLangMessagesFromList(entityType);

        if (langMessages == null) {
            KillerMoney.getInstance().getServer().getPluginManager().callEvent(
                    new KillerMoneySendMessageEvent(
                            player, event.getEntityConfig(), event.getEntity(), money
                    )
            );
        } else {
            KillerMoney.getInstance().getServer().getPluginManager().callEvent(
                    new KillerMoneySendMessageEvent(
                            player, event.getEntityConfig(), event.getEntity(), langMessages.getLossMessages(),
                            langMessages.isLossRandomMessage(), money
                    )
            );
        }

        System.out.println("4");
    }

}
