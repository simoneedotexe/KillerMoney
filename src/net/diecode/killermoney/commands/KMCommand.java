package net.diecode.killermoney.commands;

import net.diecode.killermoney.commands.subcommands.*;
import net.diecode.killermoney.enums.KMPermission;
import net.diecode.killermoney.enums.LanguageString;
import net.diecode.killermoney.enums.SenderType;
import net.diecode.killermoney.managers.CommandManager;
import net.diecode.killermoney.managers.LanguageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class KMCommand implements CommandExecutor {

    private static HashMap<String, CommandManager> subCommands = new HashMap<String, CommandManager>();

    public KMCommand() {
        registerSubCommand("help", new HelpCommand());
        registerSubCommand("reload", new ReloadCommand());
        registerSubCommand("multiplier", new MultiplierCommand());
        registerSubCommand("limit", new LimitCommand());
        registerSubCommand("info", new InfoCommand());
    }

    @Override
    public boolean onCommand(final CommandSender cs, Command cmd, String s, String[] tmpArgs) {
        CommandManager cm;

        if (tmpArgs.length == 0) {
            cm = CommandManager.getCommand("help");

            if (cm != null) {
                cm.run(cs, null);
            }

            return true;
        }

        cm = CommandManager.getCommand(tmpArgs[0]);

        if (cm != null) {
            if (cm.getSenderType() == SenderType.PLAYER) {
                if (!(cs instanceof Player)) {
                    LanguageManager.send(cs, LanguageString.COMMANDS_THIS_COMMAND_ONLY_USABLE_BY_PLAYER);

                    return true;
                }
            }

            if (cm.getSenderType() == SenderType.CONSOLE) {
                if (!(cs instanceof ConsoleCommandSender)) {
                    LanguageManager.send(cs, LanguageString.COMMANDS_THIS_COMMAND_ONLY_USABLE_BY_CONSOLE);

                    return true;
                }
            }

            if (cm.getPermission() != null && !cs.hasPermission(cm.getPermission().get())
                    && !cs.hasPermission(KMPermission.ADMIN.get())) {
                LanguageManager.send(cs, LanguageString.COMMANDS_YOU_HAVE_NOT_ENOUGH_PERMISSION,
                        cm.getPermission().get());

                return true;
            }

            if (tmpArgs.length <= cm.getMinArgs()) {
                // too few args
                if (cm.getUsage() != null) {
                    cs.sendMessage(cm.getUsage());
                }

                return true;
            }

            String[] args = new String[tmpArgs.length - 1];

            for (int i = 0; i < args.length; i++) {
                args[i] = tmpArgs[i + 1];
            }

            cm.run(cs, args);
        } else {
            HelpCommand.showHelp(cs);
        }

        return true;
    }

    private static void registerSubCommand(String subCommand, CommandManager cc) {
        if (!KMCommand.getSubCommands().containsKey(subCommand)) {
            KMCommand.getSubCommands().put(subCommand, cc);
        }
    }

    public static HashMap<String, CommandManager> getSubCommands() {
        return subCommands;
    }
}
