package net.diecode.KillerMoney.Functions;

import net.diecode.KillerMoney.Configs.Configs;
import net.diecode.KillerMoney.CustomEvents.KillerMoneyMoneyRewardEvent;
import net.diecode.KillerMoney.CustomEvents.KillerMoneySendMessageEvent;
import net.diecode.KillerMoney.CustomObjects.LangMessages;
import net.diecode.KillerMoney.KillerMoney;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class MoneyReward implements Listener {

    @EventHandler (priority = EventPriority.MONITOR)
    public void onMoneyReward(KillerMoneyMoneyRewardEvent event) {

        if (event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();
        EntityType entityType = event.getEntity().getType();
        double money = (event.getMoney() * Configs.getGlobalMoneyMultiplier());

        KillerMoney.getEconomy().depositPlayer(player, money);

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
                            player, event.getEntityConfig(), event.getEntity(), langMessages.getRewardMessages(),
                            langMessages.isRewardRandomMessage(), money
                    )
            );
        }

    }

}
