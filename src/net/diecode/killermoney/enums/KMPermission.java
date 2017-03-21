package net.diecode.killermoney.enums;

public enum KMPermission {

    ADMIN("admin"),
    MONEY_MULTIPLIER("multiplier.money"),

    LIMIT_MONEY_MULTIPLIER("multiplier.limit.money"),
    LIMIT_ITEM_MULTIPLIER("multiplier.limit.item"),
    LIMIT_COMMAND_MULTIPLIER("multiplier.limit.command"),

    BYPASS_MONEY_LIMIT("bypass.limit.money"),
    BYPASS_ITEM_LIMIT("bypass.limit.item"),
    BYPASS_COMMAND_LIMIT("bypass.limit.command"),
    BYPASS_MONEY_LIMIT_CASH_TRANSFER("bypass.limit.money.cashtransfer");

    private String perm;

    KMPermission(String perm) {
        this.perm = perm;
    }

    public String get() {
        return "killermoney." + perm;
    }

}
