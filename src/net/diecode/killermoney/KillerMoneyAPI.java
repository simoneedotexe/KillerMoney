package net.diecode.killermoney;

import net.diecode.killermoney.configs.DefaultConfig;

public class KillerMoneyAPI {

    /**
     *
     * @return Instance of main class
     */
    public static BukkitMain getMainClass() {
        return BukkitMain.getInstance();
    }

    /**
     *
     * @return Instance of config loader
     */
    public static DefaultConfig getConfig() {
        return DefaultConfig.getInstance();
    }



}
