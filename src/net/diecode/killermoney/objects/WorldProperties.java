package net.diecode.killermoney.objects;

import java.util.ArrayList;

public class WorldProperties {

    private ArrayList<String> worlds;
    private MoneyProperties moneyProperties;
    private CCommandProperties cCommandProperties;
    private CItemProperties cItemProperties;

    public WorldProperties(ArrayList<String> worlds, MoneyProperties moneyProperties,
                           CCommandProperties cCommandProperties, CItemProperties cItemProperties) {
        //this.runMethod = runMethod;
        this.worlds = worlds;
        this.moneyProperties = moneyProperties;
        this.cCommandProperties = cCommandProperties;
        this.cItemProperties = cItemProperties;
    }

    /*
    public RunMethod getRunMethod() {
        return runMethod;
    }
    */

    public ArrayList<String> getWorlds() {
        return worlds;
    }

    public MoneyProperties getMoneyProperties() {
        return moneyProperties;
    }

    public CCommandProperties getCCommandProperties() {
        return cCommandProperties;
    }

    public CItemProperties getCItemProperties() {
        return cItemProperties;
    }
}
