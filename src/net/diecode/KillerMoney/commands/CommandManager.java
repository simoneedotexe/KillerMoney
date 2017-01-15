package net.diecode.KillerMoney.commands;

import net.diecode.KillerMoney.enums.KMPermissions;
import net.diecode.KillerMoney.enums.SenderType;

import java.util.ArrayList;
import java.util.Map;

public abstract class CommandManager {

    protected KMPermissions permission;
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

    public KMPermissions getPermission() {
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

    public abstract void run(Object commandSender, String[] args);
}
