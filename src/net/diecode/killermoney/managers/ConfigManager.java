package net.diecode.killermoney.managers;

import net.diecode.killermoney.configs.DefaultConfig;
import net.diecode.killermoney.configs.EntitiesConfig;
import net.diecode.killermoney.configs.LangConfig;

public class ConfigManager {

    private static DefaultConfig defaultConfig;
    private static EntitiesConfig entitiesConfig;
    private static LangConfig langConfig;

    public static void init() {
        defaultConfig = new DefaultConfig("config.yml");
        entitiesConfig = new EntitiesConfig("entities.yml");
        langConfig = new LangConfig("lang.yml");

        checkVersionChanges();

        reloadConfigs();
    }

    private static void checkVersionChanges() {
        int version = DefaultConfig.getConfigVersion();

        if (version < 1) {
            // todo Config for 1.12
            // Parrot lang.yml
            // Parrot entities.yml

            version++;
        }

        if (version != DefaultConfig.getConfigVersion()) {
            setConfigVersion(version);
        }
    }

    private static void setConfigVersion(int newVersion) {
        DefaultConfig.getInstance().getConfig().set("Config-version", newVersion);
        DefaultConfig.getInstance().saveConfig();
    }

    public static DefaultConfig getDefaultConfig() {
        return defaultConfig;
    }

    public static EntitiesConfig getEntitiesConfig() {
        return entitiesConfig;
    }

    public static LangConfig getLangConfig() {
        return langConfig;
    }

    public static void reloadConfigs() {
        DefaultConfig.getInstance().load();
        EntitiesConfig.getInstance().load();
        LangConfig.getInstance().load();
    }
}
