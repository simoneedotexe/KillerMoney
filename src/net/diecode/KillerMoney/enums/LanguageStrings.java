package net.diecode.KillerMoney.enums;

public enum LanguageStrings {

    ENTITIES_ZOMBIE("Zombie");

    private String string;

    LanguageStrings(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    public static boolean isValid(String str) {
        for (LanguageStrings ls : LanguageStrings.values()) {
            if (ls.name().equals(str)) {
                return true;
            }
        }

        return false;
    }

}
