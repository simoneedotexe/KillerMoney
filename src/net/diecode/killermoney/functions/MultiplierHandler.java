package net.diecode.killermoney.functions;

import net.diecode.killermoney.BukkitMain;
import net.diecode.killermoney.Utils;
import net.diecode.killermoney.enums.LanguageString;
import net.diecode.killermoney.events.KMMultiplierChangedEvent;
import net.diecode.killermoney.managers.LanguageManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitTask;

public class MultiplierHandler implements Listener {

    private static double multiplier = 1;
    private static BukkitTask timer;
    private static int minuteLeft;

    @EventHandler
    public void onMultiplierChange(KMMultiplierChangedEvent e) {
        if (e.isCancelled()) {
            return;
        }

        if (e.getNewValue() == 1) {
            cancel();

            LanguageManager.send(e.getSender(), LanguageString.MULTIPLIER_CANCEL_CUSTOM_MULTIPLIER);
        } else {
            set(e.getNewValue(), e.getMinute());

            LanguageManager.send(e.getSender(), LanguageString.MULTIPLIER_SET_NEW_MULTIPLIER_VALUE,
                    MultiplierHandler.getMultiplier(), Utils.getRemainingTimeHumanFormat(e.getMinute()));
        }
    }

    private static void set(final double newMultiplier, int minute) {
        if (timer != null) {
            timer.cancel();
        }

        multiplier = newMultiplier;
        minuteLeft = minute;

        timer = Bukkit.getScheduler().runTaskTimer(BukkitMain.getInstance(), new Runnable() {
            @Override
            public void run() {
                minuteLeft--;

                if (minuteLeft < 1) {
                    cancel();
                }
            }
        }, 20L * 60, 20L * 60);
    }

    private static void cancel() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        multiplier = 1;
        minuteLeft = 0;
    }

    public static double getMultiplier() {
        return multiplier;
    }

    public static BukkitTask getTimer() {
        return timer;
    }

    public static int getMinuteLeft() {
        return minuteLeft;
    }
}
