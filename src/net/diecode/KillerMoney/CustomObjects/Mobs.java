package net.diecode.KillerMoney.CustomObjects;

import net.diecode.KillerMoney.Configs.Configs;
import net.diecode.KillerMoney.KillerMoney;
import net.diecode.KillerMoney.Loggers.ConsoleLogger;
import net.diecode.KillerMoney.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class Mobs {

    private static ArrayList<Mobs> mobsObjectList;
    private static int cashTransferPercent = 0;
    private static int cashTransferLimit = 0;
    private static int cashTransferChance = 100;

    static {
        initialize();
    }

    public static void destroyer() {
        if (mobsObjectList == null) {
            return;
        }

        for (Mobs mobs : mobsObjectList) {
            if (mobs.getItems() != null) {
                mobs.getItems().clear();
                mobs.setItems(null);
            }

            if (mobs.disabledOnWorlds != null) {
                mobs.disabledOnWorlds.clear();
                mobs.setDisabledOnWorlds(null);
            }

            if (mobs.customCommands != null) {
                mobs.customCommands.clear();
                mobs.setCustomCommands(null);
            }
        }

        mobsObjectList.clear();
        mobsObjectList = null;
    }

    public static void initialize() {
        mobsObjectList = new ArrayList<Mobs>();

        Configs.loadMobsConfig();
        FileConfiguration mobsConfig = Configs.getMobsConfig();

        if (mobsConfig.getConfigurationSection("") == null) {
            ConsoleLogger.warning("The mobs.yml is empty. Please fix it.");
            KillerMoney.getInstance().getServer().getPluginManager().disablePlugin(KillerMoney.getInstance());
            return;
        }

        Set<String> mobTypes = mobsConfig.getConfigurationSection("").getKeys(false);

        for (String currentMobType : mobTypes) {

            String formattedMobType = currentMobType.toUpperCase().replace(" ", "_");
            /**
             * Set up entity type
             */
            if (!Utils.containsEntityInEntityEnum(formattedMobType)) {
                ConsoleLogger.warning("Unknown mob type: " + currentMobType + " | " + formattedMobType);
                continue;
            }

            EntityType currentEntity = EntityType.valueOf(formattedMobType);

            Mobs mobObject = new Mobs();
            mobObject.setEntityType(currentEntity);

            /**
             * Set up entity permission
             */
            String entityPermission = null;
            if (mobsConfig.getString(currentMobType + ".Permission") != null) {
                entityPermission = mobsConfig.getString(currentMobType + ".Permission");
            }

            mobObject.setPermission(entityPermission);

            /**
             * Set up disabled worlds
             */
            List<String> entityDisabledOnWorlds = new ArrayList<String>();
            if (mobsConfig.getStringList(currentMobType + ".Disabled-on-these-worlds") != null) {
                entityDisabledOnWorlds = mobsConfig.getStringList(currentMobType + ".Disabled-on-these-worlds");
            }

            mobObject.setDisabledOnWorlds(entityDisabledOnWorlds);


            /**
             * Set up money chance
             */
            if (mobsConfig.get(currentMobType + ".Money") != null) {
                mobObject.setMoneyEnabled(true);
                int moneyChance = 100;
                if (mobsConfig.getString(currentMobType + ".Money.Chance") != null) {
                    moneyChance = Integer.valueOf(
                            mobsConfig.getString(currentMobType + ".Money.Chance").replaceAll("%", "")
                    );

                    if (moneyChance > 100) {
                        moneyChance = 100;
                    }

                    if (moneyChance < 1) {
                        moneyChance = 1;
                    }
                }
                mobObject.setMoneyChance(moneyChance);

                /**
                 * Set up money value
                 */
                if (mobsConfig.getString(currentMobType + ".Money.Value") != null) {
                    String moneyValue = mobsConfig.getString(currentMobType + ".Money.Value").replaceAll("\\s", "");
                    double moneyMin, moneyMax;

                    if (moneyValue.contains("?")) {
                        String[] splittedValue = moneyValue.split("\\?");
                        moneyMin = Double.valueOf(splittedValue[0]);
                        moneyMax = Double.valueOf(splittedValue[1]);
                    } else {
                        moneyMin = Double.valueOf(moneyValue);
                        moneyMax = Double.valueOf(moneyValue);
                    }

                    mobObject.setMoneyMin(moneyMin);
                    mobObject.setMoneyMax(moneyMax);
                } else {
                    ConsoleLogger.warning("Missing Money Value at " + currentMobType + " mob type. Money reward disabled.");
                    mobObject.setMoneyEnabled(false);
                }

            } else {
                mobObject.setMoneyEnabled(false);
            }


            /**
             * Set up item drop
             */
            if (mobsConfig.get(currentMobType + ".Item-drop") != null) {
                mobObject.setCustomItemDropEnabled(true);
                boolean clearDefaultDroppedItem = mobsConfig.getBoolean(
                        currentMobType + ".Item-drop.Clear-default-dropped-items"
                );
                mobObject.setClearDefaultItemEnabled(clearDefaultDroppedItem);

                if (mobsConfig.getConfigurationSection(currentMobType + ".Item-drop.Items") != null) {
                    Set<String> itemIDs = mobsConfig
                            .getConfigurationSection(currentMobType + ".Item-drop.Items")
                            .getKeys(false);

                    for (String currentItem : itemIDs) {

                        if (mobsConfig.get(currentMobType + ".Item-drop.Items." + currentItem + ".Material") == null) {
                            ConsoleLogger.warning("Missing material at " + currentMobType + " entity. Ignoring...");
                            continue;
                        }

                        String readMaterial =
                                mobsConfig.getString(currentMobType + ".Item-drop.Items." + currentItem + ".Material");

                        String[] splittedMaterial = readMaterial.split(":");

                        int data = 0;

                        if (splittedMaterial.length > 1) {
                            data = Integer.valueOf(splittedMaterial[1]);
                        }

                        Material isMaterial;

                        if (splittedMaterial[0].matches("[0-9]+")) {
                            isMaterial = Material.getMaterial(Integer.valueOf(splittedMaterial[0]));
                        } else {
                            isMaterial = Material.getMaterial(splittedMaterial[0]);
                        }

                        if (isMaterial == null) {
                            ConsoleLogger.warning("Bad Material with " + currentItem + " item number.");
                            continue;
                        }

                        String isName = null;
                        if (mobsConfig.getString(currentMobType + ".Item-drop.Items." + currentItem + ".Name") != null) {
                            isName = ChatColor.translateAlternateColorCodes(
                                    '&', mobsConfig.getString(currentMobType + ".Item-drop.Items." + currentItem + ".Name")
                            );
                        }

                        int isAmount = mobsConfig.getInt(currentMobType + ".Item-drop.Items." + currentItem + ".Amount");

                        if (isAmount < 1 || isAmount > 64) {
                            ConsoleLogger.warning("Bad amount setup at " + currentItem + " number: " + isAmount +
                                    " entity: " + currentMobType + ". Set it to 1");
                            isAmount = 1;
                        }

                        List<String> lore = new ArrayList<String>();
                        if (mobsConfig.getStringList(currentMobType + ".Item-drop.Items." + currentItem + ".Lore") != null) {
                            lore = mobsConfig.getStringList(currentMobType + ".Item-drop.Items." + currentItem + ".Lore");

                            for (int i = 0; i < lore.size(); i++) {
                                String currentLore = lore.get(i);
                                currentLore = ChatColor.translateAlternateColorCodes('&', currentLore);
                                lore.set(i, currentLore);
                            }
                        }

                        ItemStack is = new ItemStack(isMaterial, isAmount, (byte)data);
                        ItemMeta isMeta = is.getItemMeta();

                        if (isName != null) {
                            isMeta.setDisplayName(isName);
                        }

                        if (lore.size() != 0) {
                            isMeta.setLore(lore);
                        }

                        is.setItemMeta(isMeta);

                        List<String> enchantList = new ArrayList<String>();
                        if (mobsConfig.get(currentMobType + ".Item-drop.Items." + currentItem + ".Enchantments") != null) {
                            enchantList = mobsConfig.getStringList(currentMobType + ".Item-drop.Items." + currentItem + ".Enchantments");
                        }

                        for (String currentEnchantData : enchantList) {

                            String[] splitted = currentEnchantData.split(" ");

                            if (splitted.length < 2) {
                                ConsoleLogger.info("Bad enchantment settings: " + currentEnchantData);
                                continue;
                            }

                            String enchant = splitted[0];
                            int enchantLevel = Integer.valueOf(splitted[1]);

                            Enchantment enchantment = Enchantment.getByName(enchant);
                            if (enchantment == null) {
                                ConsoleLogger.info("Bad enchantment: " + currentEnchantData);
                                continue;
                            }

                            if (enchantLevel < enchantment.getStartLevel()) {
                                enchantLevel = enchantment.getStartLevel();
                            }

                            if (enchantLevel > enchantment.getMaxLevel()) {
                                enchantLevel = enchantment.getMaxLevel();
                            }

                            if (enchantment.canEnchantItem(is)) {
                                is.addEnchantment(enchantment, enchantLevel);
                            } else {
                                ConsoleLogger.info("Incompatible enchantment: " + enchant );
                            }
                        }

                        int itemDropChance = 100;
                        if (mobsConfig.getString(currentMobType + ".Item-drop.Items." + currentItem + ".Chance") != null) {
                            itemDropChance = Integer.valueOf(
                                    mobsConfig.getString(currentMobType + ".Item-drop.Items." + currentItem + ".Chance")
                                            .replace("%", "")
                            );
                        }

                        List<String> itemDisabledOnTheseWorlds = new ArrayList<String>();
                        if (mobsConfig.getStringList(currentMobType + ".Item-drop.Items." + currentItem + ".Dont-drop-on-these-worlds") != null) {
                            itemDisabledOnTheseWorlds = mobsConfig.getStringList(currentMobType + ".Item-drop.Items." + currentItem + ".Dont-drop-on-these-worlds");
                        }

                        String itemPermission = null;
                        if (mobsConfig.getString(currentMobType + ".Item-drop.Items." + currentItem + ".Permission") != null) {
                            itemPermission = mobsConfig.getString(currentMobType + ".Item-drop.Items." + currentItem + ".Permission");
                        }

                        CustomItems customItem = new CustomItems();
                        customItem.setId(currentItem);
                        customItem.setItem(is);
                        customItem.setChance(itemDropChance);
                        customItem.setDisabledWorlds(itemDisabledOnTheseWorlds);
                        customItem.setPermission(itemPermission);

                        mobObject.getItems().add(customItem);
                    }
                }
            } else {
                mobObject.setClearDefaultItemEnabled(false);
                mobObject.setCustomItemDropEnabled(false);
            }


            /**
             * Set up Custom Commands
             */
            if (mobsConfig.get(currentMobType + ".Custom-commands") != null) {
                mobObject.setCustomCommandsEnabled(true);
                if (mobsConfig.getStringList(currentMobType + ".Custom-commands") != null) {
                    List<String> customCommands = mobsConfig.getStringList(currentMobType + ".Custom-commands");
                    mobObject.setCustomCommands(customCommands);
                } else {
                    mobObject.setCustomCommandsEnabled(false);
                }
            } else {
                mobObject.setCustomCommandsEnabled(false);
            }

            getMobsObjectList().add(mobObject);

            /**
             * Set up Cash Transfer settings
             */
            if (currentEntity == EntityType.PLAYER) {
                if (mobsConfig.getString(currentMobType + ".Cash-transfer.Percent") != null) {
                    cashTransferPercent = Integer.valueOf(
                            mobsConfig.getString(currentMobType + ".Cash-transfer.Percent").replace("%", "")
                    );

                    if (cashTransferPercent > 100) {
                        cashTransferPercent = 100;
                    }

                    if (cashTransferPercent < 0) {
                        cashTransferPercent = 0;
                    }
                }

                cashTransferLimit = mobsConfig.getInt(currentMobType + ".Cash-transfer.Limit");

                if (cashTransferLimit < 0) {
                    cashTransferLimit = 0;
                }

                if (mobsConfig.getString(currentMobType + ".Cash-transfer.Chance") != null) {
                    cashTransferChance = Integer.valueOf(
                            mobsConfig.getString(currentMobType + ".Cash-transfer.Chance").replace("%", "")
                    );
                }

            }
        }

    }

    private EntityType entityType;

    private String permission;

    private List<String> disabledOnWorlds;

    private int moneyChance;
    private double moneyMin;
    private double moneyMax;
    private boolean moneyEnabled;

    private boolean clearDefaultItemEnabled;
    private ArrayList<CustomItems> items = new ArrayList<CustomItems>();
    private boolean customItemDropEnabled;

    private List<String> customCommands;
    private boolean customCommandsEnabled;

    public static boolean containsEntityInInstances(EntityType entity) {
        for (Mobs currentMobType : getMobsObjectList()) {
            EntityType currentEntity = currentMobType.getEntityType();
            if (entity == currentEntity) {
                return true;
            }
        }
        return false;
    }

    public static Mobs getMobsFromList(EntityType entity) {
        for (Mobs currentMobType : getMobsObjectList()) {
            EntityType currentEntity = currentMobType.getEntityType();
            if (entity == currentEntity) {
                return currentMobType;
            }
        }
        return null;
    }

    public static ArrayList<Mobs> getMobsObjectList() {
        return mobsObjectList;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    private void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public int getMoneyChance() {
        return moneyChance;
    }

    public void setMoneyChance(int moneyChance) {
        this.moneyChance = moneyChance;
    }

    public double getMoneyMin() {
        return moneyMin;
    }

    public void setMoneyMin(double moneyMin) {
        this.moneyMin = moneyMin;
    }

    public double getMoneyMax() {
        return moneyMax;
    }

    public void setMoneyMax(double moneyMax) {
        this.moneyMax = moneyMax;
    }

    public boolean isClearDefaultItemEnabled() {
        return clearDefaultItemEnabled;
    }

    public void setClearDefaultItemEnabled(boolean clearDefaultItemEnabled) {
        this.clearDefaultItemEnabled = clearDefaultItemEnabled;
    }

    public ArrayList<CustomItems> getItems() {
        return items;
    }

    public void setItems(ArrayList<CustomItems> items) {
        this.items = items;
    }

    public List<String> getCustomCommands() {
        return customCommands;
    }

    public void setCustomCommands(List<String> customCommands) {
        this.customCommands = customCommands;
    }

    public boolean isMoneyEnabled() {
        return moneyEnabled;
    }

    public void setMoneyEnabled(boolean moneyEnabled) {
        this.moneyEnabled = moneyEnabled;
    }

    public boolean isCustomItemDropEnabled() {
        return customItemDropEnabled;
    }

    public void setCustomItemDropEnabled(boolean customItemDropEnabled) {
        this.customItemDropEnabled = customItemDropEnabled;
    }

    public boolean isCustomCommandsEnabled() {
        return customCommandsEnabled;
    }

    public void setCustomCommandsEnabled(boolean customCommandsEnabled) {
        this.customCommandsEnabled = customCommandsEnabled;
    }

    public List<String> getDisabledOnWorlds() {
        return disabledOnWorlds;
    }

    public void setDisabledOnWorlds(List<String> disabledOnWorlds) {
        this.disabledOnWorlds = disabledOnWorlds;
    }

    public static int getCashTransferPercent() {
        return cashTransferPercent;
    }

    public static int getCashTransferLimit() {
        return cashTransferLimit;
    }

    public static int getCashTransferChance() {
        return cashTransferChance;
    }
}
