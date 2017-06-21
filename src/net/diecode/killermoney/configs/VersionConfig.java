package net.diecode.killermoney.configs;

import net.diecode.killermoney.BukkitMain;
import net.diecode.killermoney.managers.ConfigManager;

public class VersionConfig extends ConfigManager {

    private static VersionConfig instance;
    private static String versionStr;

    public VersionConfig(String fileName) {
        super(fileName);

        instance = this;

        load();
    }

    @Override
    public void load() {
        reload();

        versionStr = getConfig().getString("Version");

        System.out.println("VERSION STR: " + versionStr);

        if (versionStrLowerThan("4.1.0")) {
            // todo
        }

        getConfig().set("Version", BukkitMain.getInstance().getDescription().getVersion());
        saveConfig();
    }

    private static boolean versionStrLowerThan(String version) {
        if (versionStr == null || versionStr.isEmpty()) {
            return true;
        }

        // todo Temporary solution, need modification

        String[] splittedSavedVersion = versionStr.split("\\.");
        String[] splittedDiffVersion = version.split("\\.");

        for (int i = 0; i < 3; i++) {
            int saved = Integer.parseInt(splittedSavedVersion[0]);
            int diff = Integer.parseInt(splittedDiffVersion[0]);

            if (saved < diff) {
                return true;
            }

        }

        return false;
    }

    public static VersionConfig getInstance() {
        return instance;
    }

}
