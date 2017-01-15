package net.diecode.KillerMoney.commands.subcommands;

import net.diecode.KillerMoney.managers.CommandManager;

public class HelpCommand extends CommandManager {

    public HelpCommand() {
        minArgs = 0;
    }

    @Override
    public void run(Object commandSender, String[] args) {
        // todo show help
    }
}
