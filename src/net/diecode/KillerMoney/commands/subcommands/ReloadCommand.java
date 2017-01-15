package net.diecode.KillerMoney.commands.subcommands;

import net.diecode.KillerMoney.managers.CommandManager;
import net.diecode.KillerMoney.enums.KMPermissions;

public class ReloadCommand extends CommandManager {

    public ReloadCommand() {
        permission = KMPermissions.ADMIN;
        minArgs = 0;
    }

    @Override
    public void run(Object commandSender, String[] args) {
        // todo reload handler
    }
}
