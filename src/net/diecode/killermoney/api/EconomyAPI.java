package net.diecode.killermoney.api;

import net.diecode.killermoney.functions.MoneyHandler;
import net.diecode.killermoney.managers.EconomyManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EconomyAPI {

    /**
     *
     * @param itemStack
     * @return
     */
    public static boolean isMoneyItem(ItemStack itemStack) {
        return MoneyHandler.isMoneyItem(itemStack);
    }

    /**
     *
     * @param player
     * @return
     */
    public static double getMoneyMultiplier(Player player) {
        return MoneyHandler.getMoneyMultiplier(player);
    }

    /**
     *
     * @param player
     * @return
     */
    public static double getLimitMultiplier(Player player) {
        return MoneyHandler.getLimitMultiplier(player);
    }

    /**
     *
     * @param player
     * @return
     */
    public static boolean hasMoneyLimitBypass(Player player) {
        return MoneyHandler.hasMoneyLimitBypass(player);
    }

    /**
     *
     * @param player
     * @param amount
     */
    public static void deposit(Player player, double amount) {
        EconomyManager.deposit(player, amount);
    }

    /**
     *
     * @param player
     * @param amount
     */
    public static void withdraw(Player player, double amount) {
        EconomyManager.withdraw(player, amount);
    }
}
