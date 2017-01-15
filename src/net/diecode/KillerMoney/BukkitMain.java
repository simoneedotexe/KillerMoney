package net.diecode.KillerMoney;

import net.diecode.KillerMoney.commands.KMCommand;
import net.diecode.KillerMoney.configs.LangConfig;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class BukkitMain extends JavaPlugin {

    private static BukkitMain instance;
    private static LangConfig langConfig;

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
        langConfig = new LangConfig("lang.yml");

        initMetrics();

        getCommand("km").setExecutor(new KMCommand());
    }

    @Override
    public void onDisable() {
        instance = null;
        langConfig = null;
    }

    public static BukkitMain getInstance() {
        return instance;
    }
}
