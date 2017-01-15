package net.diecode.KillerMoney.enums;

public enum KMPermissions {

    ADMIN("admin");

    private String perm;

    KMPermissions(String perm) {
        this.perm = perm;
    }

    public String get() {
        return "killermoney." + perm;
    }

}
