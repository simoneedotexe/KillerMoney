package net.diecode.KillerMoney.CustomObjects;

public class CustomCommand {

    private String command;
    private int chance;

    public CustomCommand(String command, int chance) {
        this.command = command;
        this.chance = chance;
    }

    public String getCommand() {
        return command;
    }

    public int getChance() {
        return chance;
    }
}
