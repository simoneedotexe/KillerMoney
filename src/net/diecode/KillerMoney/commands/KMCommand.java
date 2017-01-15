package net.diecode.KillerMoney.commands;

import net.diecode.KillerMoney.commands.subcommands.HelpCommand;
import net.diecode.KillerMoney.commands.subcommands.ReloadCommand;
import net.diecode.KillerMoney.enums.KMPermissions;
import net.diecode.KillerMoney.enums.SenderType;
import net.diecode.KillerMoney.managers.CommandManager;
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
                    // todo msg

                    return true;
                }
            }

            if (cm.getSenderType() == SenderType.CONSOLE) {
                if (!(cs instanceof ConsoleCommandSender)) {
                    // todo msg

                    return true;
                }
            }

            if (cm.getPermission() != null && !cs.hasPermission(cm.getPermission().get())
                    && !cs.hasPermission(KMPermissions.ADMIN.get())) {
                // todo msg

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
            // todo msg
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
