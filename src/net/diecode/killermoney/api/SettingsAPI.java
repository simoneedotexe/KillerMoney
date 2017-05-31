package net.diecode.killermoney.api;

import net.diecode.killermoney.configs.DefaultConfig;
import net.diecode.killermoney.configs.LangConfig;

public class SettingsAPI {

    /**
     *
     * @return Instance of config loader
     */
    public static DefaultConfig getDefaultConfig() {
        return DefaultConfig.getInstance();
    }

    /**
     *
     * @return Instance of lang config
     */
    public static LangConfig getLangConfig() {
        return LangConfig.getInstance();
    }

}
