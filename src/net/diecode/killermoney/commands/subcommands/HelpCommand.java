package net.diecode.killermoney.commands.subcommands;

import net.diecode.killermoney.enums.SenderType;
import net.diecode.killermoney.managers.CommandManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class HelpCommand extends CommandManager {

    public HelpCommand() {
        minArgs = 0;
        senderType = SenderType.ANYONE;
    }

    @Override
    public void run(CommandSender cs, String[] args) {
        showHelp(cs);
    }

    public static void showHelp(CommandSender sender) {
        sender.sendMessage(ChatColor.GREEN + "[ " + ChatColor.GRAY + "KillerMoney" + ChatColor.GREEN + " ]");
        sender.sendMessage(ChatColor.GREEN + "/km help" + ChatColor.GRAY + " | This help messages");
        sender.sendMessage(ChatColor.GREEN + "/km info" + ChatColor.GRAY + " | Show generals info about KillerMoney");
        sender.sendMessage(ChatColor.GREEN + "/km reload" + ChatColor.GRAY + " | Reload configs");
        sender.sendMessage(ChatColor.GREEN + "/km multiplier <function> [value]" + ChatColor.GRAY + " | Set custom multiplier for given time");
    }
}
