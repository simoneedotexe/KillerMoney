package net.diecode.killermoney;

import net.diecode.killermoney.configs.DefaultConfig;
import net.diecode.killermoney.enums.LanguageString;
import net.diecode.killermoney.functions.MoneyHandler;
import net.minechart.enums.CollectingMode;
import net.minechart.enums.RepeatTime;
import net.minechart.enums.StorageType;
import net.minechart.shared.dataprocessor.GraphData;
import net.minechart.shared.exceptions.InvalidDataNameException;

import java.util.concurrent.ConcurrentHashMap;

public class MineChartGraphs {

    private static String money = "KM-Money";

    public MineChartGraphs() {
        if (DefaultConfig.getEnabledGraphs().contains("MONEY")) {
            try {
                new GraphData(money, RepeatTime.MINUTE_15, StorageType.HISTORY, CollectingMode.ASYNC,
                        LanguageString.GENERAL_CURRENCY.getString()) {
                    @Override
                    public ConcurrentHashMap<String, Object> collect() {
                        return new ConcurrentHashMap<String, Object>() {
                            {
                                put("Earned money", MoneyHandler.getEarnedMoney().doubleValue());
                                put("Lost money", MoneyHandler.getLostMoney().doubleValue());

                                MoneyHandler.resetMoneyCounter();
                            }
                        };
                    }
                };
            } catch (InvalidDataNameException e) {
                Logger.warning("Invalid data name: \"" + money + "\". Graph cannot be initialized.");

                e.getStackTrace();
            }
        }
    }
}
