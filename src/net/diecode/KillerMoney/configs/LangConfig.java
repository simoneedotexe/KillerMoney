package net.diecode.KillerMoney.configs;

import net.diecode.KillerMoney.enums.LanguageStrings;
import net.diecode.KillerMoney.managers.ConfigManager;
import org.bukkit.ChatColor;

import java.util.HashMap;

public class LangConfig extends ConfigManager {

    private static LangConfig instance;
    private static HashMap<LanguageStrings, String> strings = new HashMap<LanguageStrings, String>();

    public LangConfig(String fileName) {
        super(fileName);

        instance = this;

        load();
    }

    private void generate() {
        for (LanguageStrings ls : LanguageStrings.values()) {
            int firstLine = ls.name().indexOf("_");
            String parent = ls.name().substring(0, firstLine);
            String post = ls.name().substring(firstLine + 1, ls.name().length());

            if (!getConfig().isSet(parent + "." + post)) {
                getConfig().set(parent + "." + post, ls.getString());
            }

            saveConfig();
        }
    }

    @Override
    public void load() {
        generate();

        if (getConfig().getConfigurationSection("") == null) {
            return;
        }

        for (String parent : getConfig().getConfigurationSection("").getKeys(false)) {
            if (getConfig().getConfigurationSection(parent) == null) {
                continue;
            }

            for (String langString : getConfig().getConfigurationSection(parent).getKeys(false)) {
                String str = (parent + "." + langString).replaceFirst("\\.", "_").toUpperCase();

                if (!LanguageStrings.isValid(str)) {
                    continue;
                }

                strings.put(LanguageStrings.valueOf(str),
                        ChatColor.translateAlternateColorCodes('&', getConfig().getString(parent + "." + langString)));
            }
        }
    }

    public static LangConfig getInstance() {
        return instance;
    }

    public static HashMap<LanguageStrings, String> getStrings() {
        return strings;
    }
}
