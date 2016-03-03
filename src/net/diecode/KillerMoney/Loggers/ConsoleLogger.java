package net.diecode.KillerMoney.Loggers;

import java.util.logging.Logger;

public class ConsoleLogger {

    private static Logger log = Logger.getLogger("Minecraft");
    private static String consolePrefix = "[KillerMoney] ";
    
    public static void info(String message) {
        log.info(consolePrefix + message);
    }
    
    public static void warning(String message) {
        log.warning(consolePrefix + message);
    }
}
