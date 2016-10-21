package net.diecode.KillerMoney.Enums;

import org.bukkit.entity.EntityType;

public enum MobType {

    PASSIVE, NEUTRAL, HOSTILE, UTILITY, BOSS, PLAYER;

    public static MobType getType(EntityType entity) {
        switch (entity) {
            case BAT:           return PASSIVE;
            case CHICKEN:       return PASSIVE;
            case COW:           return PASSIVE;
            case MUSHROOM_COW:  return PASSIVE;
            case PIG:           return PASSIVE;
            case RABBIT:        return PASSIVE;
            case SHEEP:         return PASSIVE;
            case HORSE:         return PASSIVE;
            case SQUID:         return PASSIVE;
            case VILLAGER:      return PASSIVE;
            case OCELOT:        return PASSIVE;
            case WOLF:          return PASSIVE;

            case CAVE_SPIDER:   return NEUTRAL;
            case ENDERMAN:      return NEUTRAL;
            case POLAR_BEAR:    return NEUTRAL;
            case SPIDER:        return NEUTRAL;
            case PIG_ZOMBIE:    return NEUTRAL;

            case ZOMBIE:        return HOSTILE;
            case BLAZE:         return HOSTILE;
            case CREEPER:       return HOSTILE;
            case GUARDIAN:      return HOSTILE;
            case ENDERMITE:     return HOSTILE;
            case GHAST:         return HOSTILE;
            case MAGMA_CUBE:    return HOSTILE;
            case SHULKER:       return HOSTILE;
            case SILVERFISH:    return HOSTILE;
            case SKELETON:      return HOSTILE;
            case SLIME:         return HOSTILE;
            case WITCH:         return HOSTILE;

            case IRON_GOLEM:    return UTILITY;
            case SNOWMAN:       return UTILITY;

            case ENDER_DRAGON:  return BOSS;
            case WITHER:        return BOSS;

            case PLAYER:        return PLAYER;
        }

        return null;
    }

}
