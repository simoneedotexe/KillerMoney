package net.diecode.KillerMoney.Commands;

import net.diecode.KillerMoney.Configs.Configs;
import net.diecode.KillerMoney.CustomObjects.LangMessages;
import net.diecode.KillerMoney.CustomObjects.Mobs;
import net.diecode.KillerMoney.KillerMoney;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class KillerMoneyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (strings.length < 1) {
            KillerMoney.getInstance().getServer().dispatchCommand(commandSender, "killermoney help");
            return true;
        }

        if (strings[0].equalsIgnoreCase("help")) {
            commandSender.sendMessage("");
            commandSender.sendMessage(ChatColor.DARK_GRAY + ">>>" + ChatColor.RED + " KillerMoney info " + ChatColor.DARK_GRAY + "<<<");
            commandSender.sendMessage("");
            commandSender.sendMessage(ChatColor.DARK_GRAY + ">" + ChatColor.RED + " /killermoney help " + ChatColor.GRAY + " | " + ChatColor.YELLOW + "Show all available commands");
            commandSender.sendMessage(ChatColor.DARK_GRAY + ">" + ChatColor.RED + " /killermoney info " + ChatColor.GRAY + " | " + ChatColor.YELLOW + "Show plugin informations");
            commandSender.sendMessage(ChatColor.DARK_GRAY + ">" + ChatColor.RED + " /killermoney reload " + ChatColor.GRAY + " | " + ChatColor.YELLOW + "Reload all configuration files");
            return true;
        }

        if (strings[0].equalsIgnoreCase("reload")) {
            if (!commandSender.isOp() || !commandSender.hasPermission("killermoney.admin")) {
                commandSender.sendMessage("You have not enough permission");
                return true;
            }

            Configs.loadDefaultConfig();

            Mobs.destroyer();
            LangMessages.destroyer();

            Mobs.initialize();
            LangMessages.initialize();

            commandSender.sendMessage("Configs reloaded");
            return true;
        }

        if (strings[0].equalsIgnoreCase("info")) {
            commandSender.sendMessage("");
            commandSender.sendMessage(ChatColor.DARK_GRAY + ">>>" + ChatColor.RED + " KillerMoney info " + ChatColor.DARK_GRAY + "<<<");
            commandSender.sendMessage("");
            commandSender.sendMessage(ChatColor.DARK_GRAY + ">" + ChatColor.RED + " Plugin name: " + ChatColor.YELLOW + "KillerMoney");
            commandSender.sendMessage(ChatColor.DARK_GRAY + ">" + ChatColor.RED + " version: " + ChatColor.YELLOW + "v" + KillerMoney.getInstance().getDescription().getVersion());
            commandSender.sendMessage(ChatColor.DARK_GRAY + ">" + ChatColor.RED + " author: " + ChatColor.YELLOW + "diecode" + ChatColor.RED + " | " + ChatColor.YELLOW + "www.diecode.net");
            commandSender.sendMessage(ChatColor.DARK_GRAY + ">" + ChatColor.RED + " website: " + ChatColor.YELLOW + KillerMoney.getInstance().getDescription().getWebsite());
            commandSender.sendMessage("");
            return true;
        }

        return true;
    }
}
