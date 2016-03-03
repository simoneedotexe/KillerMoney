package net.diecode.KillerMoney.Functions;

import net.diecode.KillerMoney.CustomEvents.KillerMoneySendMessageEvent;
import net.diecode.KillerMoney.CustomObjects.LangMessages;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.Random;

public class SendMessage implements Listener {

    @EventHandler (priority = EventPriority.MONITOR)
    public void onSendRewardMessage(KillerMoneySendMessageEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (event.getMessages() == null) {
            return;
        }

        Player player = event.getPlayer();
        String prefix = LangMessages.getPrefix();
        String currency = LangMessages.getCurrency();
        ArrayList<String> messages = new ArrayList<String>(event.getMessages());

        if (event.getMessages() != null) {
            messages = event.getMessages();
        }

        double money = event.getMoney();
        boolean randomMessage = event.isRandomMessage();

        if (randomMessage) {
            String currentMessage = messages.get(new Random().nextInt(messages.size()));

            currentMessage = currentMessage
                    .replace("{prefix}", prefix)
                    .replace("{money}", String.valueOf(money))
                    .replace("{currency}", currency)
                    .replace("{killer-name}", player.getName());

            if (event.getEntity().getType() == EntityType.PLAYER) {
                currentMessage = currentMessage
                        .replace("{victim-name}", ((Player)event.getEntity()).getPlayer().getName());
            }

            player.sendMessage(currentMessage);
        } else {
            for (String currentMessage : messages) {

                currentMessage = currentMessage
                        .replace("{prefix}", prefix)
                        .replace("{money}", String.valueOf(money))
                        .replace("{currency}", currency)
                        .replace("{killer-name}", player.getName());

                if (event.getEntity().getType() == EntityType.PLAYER) {
                    currentMessage = currentMessage
                            .replace("{victim-name}", ((Player)event.getEntity()).getPlayer().getName());
                }

                player.sendMessage(currentMessage);
            }
        }
    }

}
