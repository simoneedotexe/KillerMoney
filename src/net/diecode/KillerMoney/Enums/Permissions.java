package net.diecode.KillerMoney.Enums;

public enum Permissions {

    ADMIN("admin"),
    MULTIPLIER("multiplier");

    private String perm;

    Permissions(String perm) {
        this.perm = perm;
    }

    public String getPerm() {
        return "killermoney." + perm;
    }

}
