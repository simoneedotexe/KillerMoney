package net.diecode.KillerMoney.Configs;

import net.diecode.KillerMoney.KillerMoney;
import net.diecode.KillerMoney.Loggers.ConsoleLogger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Configs {

    private static String locale;
    private static boolean updateCheck;
    private static int decimalPlaces = 1;
    private static boolean disableSpawnerFarming;
    private static boolean disableEggFarming;
    private static int globalMoneyMultiplier = 1;
    private static ArrayList<String> globalDisabledWorlds;
    private static boolean globalDisableFunctionInCreativeMode;
    private static boolean hookMobArena;
    private static boolean processConfigInArena;
    private static boolean hookMineStatus;
    private static ArrayList<String> enabledGraphs = new ArrayList<String>();

    private static File versionFile = null;
    private static FileConfiguration versionConfig;
    private static String versionFileName = "version.yml";

    private static File mobsFile = null;
    private static FileConfiguration mobsConfig;
    private static String mobsFileName = "mobs.yml";

    private static File langFile = null;
    private static FileConfiguration langConfig;

    public static void initializeConfigs() {
        loadVersionConfig();
        loadDefaultConfig();
    }

    public static void loadDefaultConfig() {
        KillerMoney.getInstance().saveDefaultConfig();
        KillerMoney.getInstance().reloadConfig();

        FileConfiguration config = KillerMoney.getInstance().getConfig();

        versionCheck();

        /**
         * Locale setting
         */
        locale = config.getString("Locale");

        /**
         * Decimal places
         */
        decimalPlaces = config.getInt("Decimal-places");

        /**
         * Check update setting
         */
        updateCheck = config.getBoolean("Check-update");

        /**
         * Farming settings
         */
        disableSpawnerFarming = config.getBoolean("Farming.Disable-spawner-farming");
        disableEggFarming = config.getBoolean("Farming.Disable-egg-farming");

        /**
         * Global money multiplier setting
         */
        globalMoneyMultiplier = config.getInt("Global-settings.Money-multiplier");

        if (globalMoneyMultiplier < 1) {
            globalMoneyMultiplier = 1;
        }

        /**
         * Disabled Worlds
         */
        globalDisabledWorlds = new ArrayList<String>(config.getStringList("Global-settings.Disable-functions-on-these-worlds"));

        /**
         * Disable function in Creative mode
         */
        globalDisableFunctionInCreativeMode = config.getBoolean("Global-settings.Disable-functions-in-creative-mode");

        /**
         * Mob Arena hook settings
         */
        hookMobArena = config.getBoolean("Hook.MobArena.Enabled");
        processConfigInArena = config.getBoolean("Hook.MobArena.Config-processing-when-players-in-arena");

        hookMineStatus = config.getBoolean("Hook.MineStatus.Enabled");

        for (String s : config.getConfigurationSection("Hook.MineStatus.Graphs").getKeys(false)) {
            if (config.getBoolean("Hook.MineStatus.Graphs." + s)) {
                enabledGraphs.add(s);
            }
        }
    }

    public static void loadVersionConfig() {
        try {
            versionFile = new File(KillerMoney.getInstance().getDataFolder(), versionFileName);

            if (!versionFile.exists()) {
                KillerMoney.getInstance().saveResource(versionFileName, false);
            }

        } catch (IllegalArgumentException iaex) {
            iaex.printStackTrace();
        } finally {
            versionConfig = YamlConfiguration.loadConfiguration(versionFile);
        }
    }

    public static void loadLangConfig() {
        String localeFileName = locale + "_locale.yml";
        try {
            langFile = new File(KillerMoney.getInstance().getDataFolder(), localeFileName);

            if (!langFile.exists()) {
                KillerMoney.getInstance().saveResource(localeFileName, false);
            }

            ConsoleLogger.info("Locale: \"" + locale + "\" | Locale file: " + localeFileName);
        } catch (IllegalArgumentException iaex) {
            langFile = new File(KillerMoney.getInstance().getDataFolder(), "en_locale.yml");

            if (!langFile.exists()) {
                KillerMoney.getInstance().saveResource("en_locale.yml", false);
            }

            ConsoleLogger.info("Wrong locale setup, using \"en\"");
        } finally {
            langConfig = YamlConfiguration.loadConfiguration(langFile);
        }
    }

    public static void loadMobsConfig() {
        try {
            mobsFile = new File(KillerMoney.getInstance().getDataFolder(), mobsFileName);

            if (!mobsFile.exists()) {
                KillerMoney.getInstance().saveResource(mobsFileName, false);
            }

        } catch (IllegalArgumentException iaex) {
            iaex.printStackTrace();
        } finally {
            mobsConfig = YamlConfiguration.loadConfiguration(mobsFile);
        }
    }

    private static void versionCheck() {
        try {
            double version = getVersionConfig().getDouble("plugin-version");
            double currentVersion = Double.parseDouble(KillerMoney.getInstance().getDescription().getVersion());

            if (version < 3.2) {
                KillerMoney.getInstance().getConfig().set("Hook.MineStatus.Enabled", true);
                KillerMoney.getInstance().getConfig().set("Hook.MineStatus.Graphs.MOB-KILLS", true);
            }

            if (version < 3.11) {
                KillerMoney.getInstance().getConfig().set("Decimal-places", 2);
                KillerMoney.getInstance().getConfig().set("Global-money-multiplier", null);
                KillerMoney.getInstance().getConfig().set("Global-settings.Money-multiplier", 1);
                KillerMoney.getInstance().getConfig().set("Global-settings.Disable-functions-on-these-worlds",
                        Arrays.asList("world_the_end"));
                KillerMoney.getInstance().getConfig().set("Global-settings.Disable-functions-in-creative-mode", true);
                KillerMoney.getInstance().saveConfig();
            }

            if (version != currentVersion) {
                getVersionConfig().set("plugin-version", currentVersion);
                getVersionConfig().save(getVersionFile());
            }

        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

    public static String getLocale() {
        return locale;
    }

    public static boolean isUpdateCheckEnabled() {
        return updateCheck;
    }

    public static int getDecimalPlaces() {
        return decimalPlaces;
    }

    public static boolean isHookMobArena() {
        return hookMobArena;
    }

    public static boolean isProcessConfigInArena() {
        return processConfigInArena;
    }

    public static boolean isDisableSpawnerFarming() {
        return disableSpawnerFarming;
    }

    public static boolean isDisableEggFarming() {
        return disableEggFarming;
    }

    public static int getGlobalMoneyMultiplier() {
        return globalMoneyMultiplier;
    }

    public static ArrayList<String> getGlobalDisabledWorlds() {
        return globalDisabledWorlds;
    }

    public static boolean isDisabledFunctionInCreative() {
        return globalDisableFunctionInCreativeMode;
    }

    public static File getLangFile() {
        return langFile;
    }

    public static FileConfiguration getLangConfig() {
        return langConfig;
    }

    public static File getMobsFile() {
        return mobsFile;
    }

    public static FileConfiguration getMobsConfig() {
        return mobsConfig;
    }

    public static File getVersionFile() {
        return versionFile;
    }

    public static FileConfiguration getVersionConfig() {
        return versionConfig;
    }

    public static boolean isHookMineStatus() {
        return hookMineStatus;
    }

    public static ArrayList<String> getEnabledGraphs() {
        return enabledGraphs;
    }
}
