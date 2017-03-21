package net.diecode.killermoney.commands.subcommands;

import net.diecode.killermoney.BukkitMain;
import net.diecode.killermoney.enums.SenderType;
import net.diecode.killermoney.managers.CommandManager;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class InfoCommand extends CommandManager {

    public InfoCommand() {
        minArgs = 0;
        aliases = new ArrayList<String>() {
            {
                add("about");
            }
        };
        senderType = SenderType.ANYONE;
    }

    @Override
    public void run(CommandSender cs, String[] args) {
        cs.sendMessage(ChatColor.GREEN + "[ " + ChatColor.GRAY + "KillerMoney about" + ChatColor.GREEN + " ]");
        cs.sendMessage(ChatColor.GRAY + "Developer: " + ChatColor.GREEN + "diecode" + ChatColor.GRAY + " | Twitter: " + ChatColor.GREEN + "@diecode");
        cs.sendMessage(ChatColor.GRAY + "Bukkit page: " + ChatColor.GREEN + "http://dev.bukkit.org/bukkit-plugins/killermoney/");
        cs.sendMessage(ChatColor.GRAY + "Plugin version: " + ChatColor.GREEN + BukkitMain.getInstance().getDescription().getVersion());
    }
}
