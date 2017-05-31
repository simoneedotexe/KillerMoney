package net.diecode.killermoney.api;

import net.diecode.killermoney.commands.KMCommand;
import net.diecode.killermoney.managers.CommandManager;

import java.util.HashMap;

public class CommandAPI {

    /**
     *
     * @return Killermoney's sub commands
     */
    public static HashMap<String, CommandManager> getSubCommands() {
        return KMCommand.getSubCommands();
    }

}
