package net.diecode.KillerMoney.managers;

import net.diecode.KillerMoney.configs.LangConfig;
import net.diecode.KillerMoney.enums.LanguageStrings;

public class LanguageManager {

    public static String cGet(LanguageStrings l1, Object... args) {
        String s = LangConfig.getStrings().get(l1);

        if (s != null) {
            for (int i = 0; i < args.length; i++) {
                String holder = "{" + i + "}";

                s = s.replace(holder, String.valueOf(args[i]));
            }

            return s;
        } else {
            return l1.name();
        }
    }

}
