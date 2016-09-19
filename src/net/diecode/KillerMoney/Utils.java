package net.diecode.KillerMoney;

import net.diecode.KillerMoney.Configs.Configs;
import net.diecode.KillerMoney.Enums.Permissions;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.math.BigDecimal;
import java.util.Random;

public class Utils {

    public static boolean chanceGenerator(int chance) {
        return (Math.random() * 100) < chance;
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

    public static int getMultiplier(Player p) {
        int multiplier = 1;

        for (PermissionAttachmentInfo pai : p.getEffectivePermissions()) {
            String perm = pai.getPermission().toLowerCase();

            if (perm.contains(Permissions.MULTIPLIER.getPerm())) {
                perm = perm.replace(Permissions.MULTIPLIER.getPerm() + ".", "");
                int tmpMultiplier = Integer.valueOf(perm);

                if (tmpMultiplier > multiplier) {
                    multiplier = tmpMultiplier;
                }
            }
        }

        return multiplier;
    }

    public static double decimalFormating(double number) {
        BigDecimal bd = new BigDecimal(number).setScale(Configs.getDecimalPlaces(), BigDecimal.ROUND_HALF_EVEN);

        return bd.doubleValue();
    }

}
