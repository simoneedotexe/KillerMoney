package net.diecode.KillerMoney;

import io.minestatus.enums.CollectingMode;
import io.minestatus.enums.RepeatTime;
import io.minestatus.enums.StorageType;
import io.minestatus.shared.dataprocessor.GraphData;
import io.minestatus.shared.exceptions.InvalidDataNameException;
import net.diecode.KillerMoney.Configs.Configs;
import net.diecode.KillerMoney.Enums.MobType;
import net.diecode.KillerMoney.Loggers.ConsoleLogger;

import java.util.concurrent.ConcurrentHashMap;

public class MSGraphs {

    private static String mobKillsName = "MOB-KILLS";

    public MSGraphs() {
        if (Configs.getEnabledGraphs().contains("MOB-KILLS")) {
            try {
                new GraphData(mobKillsName, RepeatTime.MINUTE_5, StorageType.HISTORY, CollectingMode.ASYNC) {
                    @Override
                    public ConcurrentHashMap<String, Object> collect() {
                        return new ConcurrentHashMap<String, Object>() {
                            {
                                for (MobType mt : MobType.values()) {
                                    int counter = 0;

                                    if (EntityDeath.getKilledMobTypeCounter().containsKey(mt)) {
                                        counter = EntityDeath.getKilledMobTypeCounter().get(mt);
                                    }

                                    put(mt.name(), counter);
                                }

                                EntityDeath.resetMobTypeCounter();
                            }
                        };
                    }
                };
            } catch (InvalidDataNameException e) {
                ConsoleLogger.warning("Invalid data name: \"" + mobKillsName + "\". Graph cannot be initialized.");

                e.getStackTrace();
            }
        }
    }

}
