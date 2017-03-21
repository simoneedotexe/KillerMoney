package net.diecode.killermoney.managers;

import net.diecode.killermoney.commands.KMCommand;
import net.diecode.killermoney.enums.KMPermission;
import net.diecode.killermoney.enums.SenderType;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Map;

public abstract class CommandManager {

    protected KMPermission permission;
    protected SenderType senderType;
    protected int minArgs;
    protected String usage;
    protected ArrayList<String> aliases;

    public static CommandManager getCommand(String command) {
        for (Map.Entry<String, CommandManager> entry : KMCommand.getSubCommands().entrySet()) {
            if (entry.getKey().equalsIgnoreCase(command)) {
                return entry.getValue();
            }

            if (entry.getValue().aliases != null) {
                for (String alias : entry.getValue().aliases) {
                    if (alias.equalsIgnoreCase(command)) {
                        return entry.getValue();
                    }
                }
            }
        }

        return null;
    }

    public KMPermission getPermission() {
        return permission;
    }

    public SenderType getSenderType() {
        return senderType;
    }

    public int getMinArgs() {
        return minArgs;
    }

    public String getUsage() {
        return usage;
    }

    public ArrayList<String> getAliases() {
        return aliases;
    }

    public abstract void run(CommandSender cs, String[] args);
}
