package net.diecode.KillerMoney;

import net.diecode.KillerMoney.Configs.Configs;
import net.diecode.KillerMoney.CustomObjects.LangMessages;
import net.diecode.KillerMoney.CustomObjects.Mobs;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class KillerMoneyAPI {

    /**
     * @return  Locale String - default en
     */
    public String getLocale() {
        return Configs.getLocale();
    }

    /**
     * @return  True if mobArena hooked
     */
    public boolean isMobArenaHooked() {
        return Configs.isHookMobArena();
    }

    /**
     * @return  True if process-events is enabled in config
     */
    public boolean processEventsInMobArena() {
        return Configs.isProcessConfigInArena();
    }

    /**
     * @return Decimal Places from config
     */
    public int getDecimalPlaces() {
        return Configs.getDecimalPlaces();
    }

    /**
     * @return  True if spawner farming disabled
     */
    public boolean isSpawnerFarmingDisabled() {
        return Configs.isDisableSpawnerFarming();
    }

    /**
     * @return  True if egg farming disabled
     */
    public boolean isEggFarmingDisabled() {
        return Configs.isDisableEggFarming();
    }

    /**
     * @return  Global money multiplier
     */
    public int getGlobalMoneyMultiplier() {
        return Configs.getGlobalMoneyMultiplier();
    }

    /**
     * @return  Name list of disabled worlds
     */
    public ArrayList<String> getGlobalDisabledWorlds() {
        return Configs.getGlobalDisabledWorlds();
    }

    /**
     * @return  True if disabled functions in creative mode
     */
    public boolean isCreativeModeFunctionsDisabled() {
        return Configs.isDisabledFunctionInCreative();
    }

    /**
     * @return  Mobs Config
     */
    public FileConfiguration getMobsConfig() {
        return Configs.getMobsConfig();
    }

    /**
     * @return  Locale Config
     */
    public FileConfiguration getLangConfig() {
        return Configs.getLangConfig();
    }

    /**
     * @return  Prefix from Locale config
     */
    public String getPrefix() {
        return LangMessages.getPrefix();
    }

    /**
     * @return  Currency from Locale config
     */
    public String getCurrency() {
        return LangMessages.getCurrency();
    }

    /**
     * @return  LangMessage Objects
     */
    public ArrayList<LangMessages> getMobsLangMessagesObjects() {
        return LangMessages.getLangMessagesObjectList();
    }

    /**
     *
     * @param entityType    Type of Entity, example EntityType.ZOMBIE
     * @return              LangMessage Object of Entity or null if none found
     */
    public LangMessages getMobsLangMessages(EntityType entityType) {
        for (LangMessages currentLangMessage : LangMessages.getLangMessagesObjectList()) {
            if (entityType == currentLangMessage.getEntityType()) {
                return currentLangMessage;
            }
        }
        return null;
    }

    /**
     * @return  Cash Transfer in percent
     */
    public int getCashTransferPercent() {
        return Mobs.getCashTransferPercent();
    }

    /**
     * @return  Cash Transfer limit
     */
    public int getCashTransferLimit() {
        return Mobs.getCashTransferLimit();
    }

    /**
     * @return  Cash Transfer chance
     */
    public int getCashTransferChance() {
        return Mobs.getCashTransferChance();
    }

    /**
     * @return  Mobs Objects
     */
    public ArrayList<Mobs> getMobsSettingsObjects() {
        return Mobs.getMobsObjectList();
    }

    /**
     *
     * @param entityType    Type of Entity, example EntityType.ZOMBIE
     * @return              Mobs Object of Entity or null if none found
     */
    public Mobs getMobsSettings(EntityType entityType) {
        for (Mobs currentMob : Mobs.getMobsObjectList()) {
            if (entityType == currentMob.getEntityType()) {
                return currentMob;
            }
        }

        return null;
    }

}
