package net.diecode.KillerMoney;

import com.garbagemule.MobArena.MobArenaHandler;
import net.diecode.KillerMoney.Commands.KillerMoneyCommand;
import net.diecode.KillerMoney.Configs.Configs;
import net.diecode.KillerMoney.CustomObjects.LangMessages;
import net.diecode.KillerMoney.CustomObjects.Mobs;
import net.diecode.KillerMoney.Functions.*;
import net.diecode.KillerMoney.Loggers.ConsoleLogger;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class KillerMoney extends JavaPlugin {

    private static KillerMoney plugin;
    private static Economy economy = null;
    private static Permission permission = null;
    private static MobArenaHandler maHandler = null;

    private Mobs mobs;
    private LangMessages langMessages;
    private MineChartGraphs mineChartGraphs;

    private static boolean mineChartHooked;

    private Update checkUpdate;

    public static KillerMoney getInstance() {
        return plugin;
    }

    public static Economy getEconomy() {
        return economy;
    }

    public static MobArenaHandler getMaHandler() {
        return maHandler;
    }

    private boolean initEconomy() {
        RegisteredServiceProvider<Economy> economyProvider =
                getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);

        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }

    private void initMetrics() {
        try {
            new Metrics(this).start();
            ConsoleLogger.info("Metrics initialized");
        } catch (IOException e) {
            ConsoleLogger.info("Metrics initialization failed");
        }
    }

    private void hookMobArena() {
        if (getServer().getPluginManager().getPlugin("MobArena") != null) {
            maHandler = new MobArenaHandler();
            ConsoleLogger.info("MobArena hooked");
        } else {
            ConsoleLogger.info("MobArena not found");
        }
    }

    public void hookMineChart() {
        if ((Bukkit.getPluginManager().getPlugin("MineChart") != null)
                && !Configs.getEnabledGraphs().isEmpty()) {
            mineChartHooked = true;
            mineChartGraphs = new MineChartGraphs();
        }
    }

    @Override
    public void onEnable() {
        plugin = this;

        Configs.initializeConfigs();

        checkUpdate = new Update(55732, "7b85388e796e6738738fe172679c8801d4cc2d74");

        mobs = new Mobs();
        langMessages = new LangMessages();

        initMetrics();

        if (Configs.isUpdateCheckEnabled()) {
            getServer().getScheduler().runTaskTimerAsynchronously(this, new Runnable() {
                @Override
                public void run() {
                    if (!Update.isUpdateAvailable()) {
                        checkUpdate.query();
                    }
                }
            }, 20L, 20L * 60 * 60 * 24);
        }

        if (!initEconomy()) {
            ConsoleLogger.info("Vault or economy plugin not found! Please install them, and restart server.");
            getServer().getPluginManager().disablePlugin(this);

            return;
        }

        if (Configs.isHookMobArena()) {
            hookMobArena();
        }

        getServer().getPluginManager().registerEvents(new MoneyReward(), this);
        getServer().getPluginManager().registerEvents(new MoneyLose(), this);
        getServer().getPluginManager().registerEvents(new SendMessage(), this);
        getServer().getPluginManager().registerEvents(new RunCommand(), this);
        getServer().getPluginManager().registerEvents(new CustomItemDrop(), this);
        getServer().getPluginManager().registerEvents(new CashTransfer(), this);
        getServer().getPluginManager().registerEvents(new Farming(), this);
        getServer().getPluginManager().registerEvents(new EntityDeath(), this);
        getServer().getPluginManager().registerEvents(new CommonEvents(), this);

        getCommand("killermoney").setExecutor(new KillerMoneyCommand());

        if (Configs.isHookMineChart()) {
            hookMineChart();
        }

        /*
        for (EntityType entity : EntityType.values()) {
            ConsoleLogger.info(entity.toString());
        }
        */
    }

    @Override
    public void onDisable() {
        Mobs.destroyer();
        LangMessages.destroyer();

        if (Farming.getSpawnedMobs() != null) {
            Farming.getSpawnedMobs().clear();
            Farming.setSpawnedMobs(null);
        }

        plugin = null;
        mobs = null;
        langMessages = null;
        economy = null;
        maHandler = null;
    }

    public static boolean isMineChartHooked() {
        return mineChartHooked;
    }
}
