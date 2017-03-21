package net.diecode.killermoney.commands.subcommands;

import net.diecode.killermoney.enums.KMPermission;
import net.diecode.killermoney.enums.LanguageString;
import net.diecode.killermoney.enums.SenderType;
import net.diecode.killermoney.functions.LimitHandler;
import net.diecode.killermoney.managers.CommandManager;
import net.diecode.killermoney.managers.LanguageManager;
import org.bukkit.command.CommandSender;

public class LimitCommand extends CommandManager {

    public LimitCommand() {
        permission = KMPermission.ADMIN;
        minArgs = 1;
        usage = LanguageManager.cGet(LanguageString.COMMANDS_COMMAND_LIMIT_USAGE);
        senderType = SenderType.ANYONE;
    }

    @Override
    public void run(CommandSender cs, String[] args) {
        String subCommand = args[0];

        if (subCommand.equalsIgnoreCase("reset") || subCommand.equalsIgnoreCase("clear")) {
            LimitHandler.reset();

            LanguageManager.send(cs, LanguageString.COMMANDS_LIMIT_RESET);
        }
    }
}
