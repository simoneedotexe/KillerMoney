package net.diecode.KillerMoney;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class BukkitMain extends JavaPlugin {

    private static BukkitMain instance;

    private void initMetrics() {
        try {
            new Metrics(this).start();
        } catch (IOException e) {
            // todo handle exception
        }
    }

    @Override
    public void onEnable() {
        instance = this;

        initMetrics();
    }

    @Override
    public void onDisable() {

    }

    public static BukkitMain getInstance() {
        return instance;
    }
}
