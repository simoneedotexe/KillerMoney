package net.diecode.killermoney.commands.subcommands;

import net.diecode.killermoney.configs.DefaultConfig;
import net.diecode.killermoney.configs.EntitiesConfig;
import net.diecode.killermoney.configs.LangConfig;
import net.diecode.killermoney.enums.LanguageString;
import net.diecode.killermoney.enums.SenderType;
import net.diecode.killermoney.managers.CommandManager;
import net.diecode.killermoney.enums.KMPermission;
import net.diecode.killermoney.managers.LanguageManager;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends CommandManager {

    public ReloadCommand() {
        permission = KMPermission.ADMIN;
        minArgs = 0;
        senderType = SenderType.ANYONE;
    }

    @Override
    public void run(CommandSender cs, String[] args) {
        DefaultConfig.getInstance().load();
        LangConfig.getInstance().load();
        EntitiesConfig.getInstance().load();

        LanguageManager.send(cs, LanguageString.GENERAL_CONFIGS_ARE_RELOADED);
    }
}
