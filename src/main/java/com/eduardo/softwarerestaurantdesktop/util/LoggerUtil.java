package com.eduardo.softwarerestaurantdesktop.util;

import java.util.Date;
import java.util.logging.*;

public class LoggerUtil {

    private static final Logger logger = getLogger();


    public static Logger getLogger() {
        Logger log = Logger.getLogger(LoggerUtil.class.getName());

        log.setUseParentHandlers(false);

        ConsoleHandler consoleHandler = new ConsoleHandler();
        Formatter formatter = new Formatter() {
            private static final String format = "%1$s : %2$tF : %2$tT : %3$s %n";

            @Override
            public String format(LogRecord logRecord) {
                return String.format(
                        format,
                        logRecord.getLevel(),
                        new Date(logRecord.getMillis()),
                        logRecord.getMessage()
                );
            }
        };

        consoleHandler.setFormatter(formatter);
        consoleHandler.setLevel(Level.ALL);
        log.addHandler(consoleHandler);

        return log;
    }
}
