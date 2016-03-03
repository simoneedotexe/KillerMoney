package net.diecode.KillerMoney.CustomObjects;

import net.diecode.KillerMoney.Configs.Configs;
import net.diecode.KillerMoney.Loggers.ConsoleLogger;
import net.diecode.KillerMoney.Utils;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.Set;

public class LangMessages {

    private static ArrayList<LangMessages> langMessagesObjectList;
    private static String prefix;
    private static String currency;

    static {
        initialize();
    }

    public static void destroyer() {
        if (langMessagesObjectList == null) {
            return;
        }

        for (LangMessages messages : langMessagesObjectList) {
            if (messages.getRewardMessages() != null) {
                messages.getRewardMessages().clear();
                messages.setRewardMessages(null);
            }

            if (messages.getLossMessages() != null) {
                messages.getLossMessages().clear();
                messages.setLossMessages(null);
            }

            messages = null;
        }

        langMessagesObjectList.clear();
        langMessagesObjectList = null;
    }

    public static void initialize() {
        langMessagesObjectList = new ArrayList<LangMessages>();

        Configs.loadLangConfig();
        FileConfiguration langConfig = Configs.getLangConfig();

        prefix = langConfig.getString("prefix");

        if (prefix != null) {
            prefix = ChatColor.translateAlternateColorCodes('&', prefix);
        }

        currency = langConfig.getString("currency");

        if (currency != null) {
            currency = ChatColor.translateAlternateColorCodes('&', currency);
        }

        Set<String> entityTypes = langConfig.getConfigurationSection("Entities").getKeys(false);

        for (String currentEntityType : entityTypes) {


            /**
             * Set up entity type
             */
            if (!Utils.containsEntityInEntityEnum(currentEntityType)) {
                ConsoleLogger.warning("Unknown entity type in lang file: " + currentEntityType);
                continue;
            }

            LangMessages langMessageObject = new LangMessages();

            EntityType currentEntity = EntityType.valueOf(currentEntityType);
            langMessageObject.setEntityType(currentEntity);


            /**
             * Set up Reward section
             */
            if (langConfig.get("Entities." + currentEntityType + ".Reward.Messages") != null) {
                boolean randomMessage =
                        langConfig.getBoolean("Entities." + currentEntityType + ".Reward.Random-message");

                ArrayList<String> messages = new ArrayList<String>();

                for (String currentMessage : langConfig.getStringList("Entities." + currentEntityType + ".Reward.Messages")) {
                    messages.add(ChatColor.translateAlternateColorCodes('&', currentMessage));
                }

                langMessageObject.setRewardRandomMessage(randomMessage);
                langMessageObject.setRewardMessages(messages);
            }


            /**
             * Set up Loss section
             */
            if (langConfig.get("Entities." + currentEntityType + ".Loss.Messages") != null) {
                boolean randomMessage =
                        langConfig.getBoolean("Entities." + currentEntityType + ".Loss.Random-message");

                ArrayList<String> messages = new ArrayList<String>();

                for (String currentMessage : langConfig.getStringList("Entities." + currentEntityType + ".Loss.Messages")) {
                    messages.add(ChatColor.translateAlternateColorCodes('&', currentMessage));
                }

                langMessageObject.setLossRandomMessage(randomMessage);
                langMessageObject.setLossMessages(messages);
            }

            getLangMessagesObjectList().add(langMessageObject);

            /*
            System.out.println("reward random boolean: " + langMessageObject.isRewardRandomMessage());
            System.out.println("reward messages: " + langMessageObject.getRewardMessages());
            System.out.println("---");
            System.out.println("loss random boolean: " + langMessageObject.isLossRandomMessage());
            System.out.println("loss messages: " + langMessageObject.getLossMessages());
            */
        }

        /*
        System.out.println("---");
        System.out.println("prefix: " + getPrefix());
        System.out.println("currency: " + getCurrency());
        */
    }

    private EntityType entityType;

    private boolean rewardRandomMessage;
    private ArrayList<String> rewardMessages;

    private boolean lossRandomMessage;
    private ArrayList<String> lossMessages;

    public static LangMessages getLangMessagesFromList(EntityType entity) {
        for (LangMessages currentMobType : getLangMessagesObjectList()) {
            EntityType currentEntity = currentMobType.getEntityType();
            if (entity == currentEntity) {
                return currentMobType;
            }
        }
        return null;
    }

    public static String getPrefix() {
        return prefix;
    }

    public static String getCurrency() {
        return currency;
    }

    public static void setPrefix(String prefix) {
        LangMessages.prefix = prefix;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    private void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public boolean isRewardRandomMessage() {
        return rewardRandomMessage;
    }

    public void setRewardRandomMessage(boolean rewardRandomMessage) {
        this.rewardRandomMessage = rewardRandomMessage;
    }

    public ArrayList<String> getRewardMessages() {
        return rewardMessages;
    }

    public void setRewardMessages(ArrayList<String> rewardMessages) {
        this.rewardMessages = rewardMessages;
    }

    public boolean isLossRandomMessage() {
        return lossRandomMessage;
    }

    public void setLossRandomMessage(boolean lossRandomMessage) {
        this.lossRandomMessage = lossRandomMessage;
    }

    public ArrayList<String> getLossMessages() {
        return lossMessages;
    }

    public void setLossMessages(ArrayList<String> lossMessages) {
        this.lossMessages = lossMessages;
    }

    public static ArrayList<LangMessages> getLangMessagesObjectList() {
        return langMessagesObjectList;
    }
}
