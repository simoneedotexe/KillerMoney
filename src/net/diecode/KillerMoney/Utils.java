package net.diecode.KillerMoney;

import net.diecode.KillerMoney.Configs.Configs;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.util.Random;

public class Utils {

    public static boolean chanceGenerator(int chance) {
        return (Math.random() * 100) < chance;
    }

    public static int randomNumber(int min, int max) {
        if (min == max) {
            return min;
        }

        if (max > min) {
            return new Random().nextInt((max - min) + 1) + min;
        }

        if (min > max) {
            return new Random().nextInt((min - max) + 1) + max;
        }

        return 0;
    }

    public static double randomNumber(double min, double max) {

        if (min == max) {
            return min;
        }

        double randomNumber = new Random().nextDouble();

        if (max > min) {
            return min + (max - min) * randomNumber;
        }

        if (min > max) {
            return max + (min - max) * randomNumber;
        }

        return 0;
    }

    public static boolean containsEntityInEntityEnum(String currentMobType) {
        for (EntityType et : EntityType.values()) {
            if (et.name().equalsIgnoreCase(currentMobType)) {
                return true;
            }
        }
        return false;
    }

    public static int getPermMultiplier(Player player) {
        int multiplier = 1;
        int max = 10;

        for (int i = 1; i <= max; i++) {
            if (player.hasPermission("killermoney.multiplier." + i)) {
                multiplier = i;
            }
        }

        return multiplier;
    }

    public static double decimalFormating(double number) {
        BigDecimal bd = new BigDecimal(number).setScale(Configs.getDecimalPlaces(), BigDecimal.ROUND_HALF_EVEN);

        return bd.doubleValue();
    }

}
