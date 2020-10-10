package com.github.fahjulian.stealth.core.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Log {

    private static final String ANSI_RESET = "\u001B[0m", //
            ANSI_RED = "\033[1;31m", //
            ANSI_YELLOW = "\033[1;33m", //
            ANSI_BLUE = "\033[1;34m";

    private static enum Level {
        INFO("INFO", false, ANSI_BLUE), //
        CORE_INFO("INFO", true, ANSI_BLUE), //
        WARN("WARN", false, ANSI_YELLOW), //
        CORE_WARN("WARN", true, ANSI_YELLOW), //
        ERROR("ERROR", false, ANSI_RED), //
        CORE_ERROR("ERROR", true, ANSI_RED);

        private final boolean core;
        private final String name, colorCode;

        private Level(String name, boolean core, String colorCode) {
            this.name = name;
            this.core = core;
            this.colorCode = colorCode;
        }
    }

    private File file;

    private static Log instance;

    /**
     * Initialize the logging system
     * 
     * @param fileDir The directory to save .log files to
     */
    public static void init(String fileDir) {
        try {
            String date = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date());
            instance.file = new File(fileDir + date + ".log");

            new File(fileDir).mkdirs();
            instance.file.createNewFile();
        } catch (Exception ex) {
            instance.print(Level.ERROR, "(Log) Fatal error initializing log file.");
        }

        Log.coreInfo("Log)", "Iniitalized logger.");
    }

    public static final String error(String origin, String message, Object... args) {
        message = String.format("(%s) %s", origin, message, args);
        instance.print(Level.ERROR, message);
        instance.write(Level.ERROR, message);
        return message;
    }

    public static final String coreError(String origin, String message, Object... args) {
        message = String.format("(%s) %s", origin, message, args);
        instance.print(Level.CORE_ERROR, message);
        instance.write(Level.CORE_ERROR, message);
        return message;
    }

    public static final String warn(String origin, String message, Object... args) {
        message = String.format("(%s) %s", origin, message, args);
        instance.print(Level.WARN, message);
        instance.write(Level.WARN, message);
        return message;
    }

    public static final String coreWarn(String origin, String message, Object... args) {
        message = String.format("(%s) %s", origin, message, args);
        instance.print(Level.CORE_WARN, message);
        instance.write(Level.CORE_WARN, message);
        return message;
    }

    public static final String info(String origin, String message, Object... args) {
        message = String.format("(%s) %s", origin, message, args);
        instance.print(Level.INFO, message);
        instance.write(Level.INFO, message);
        return message;
    }

    public static final String coreInfo(String origin, String message, Object... args) {
        message = String.format("(%s) %s", origin, message, args);
        instance.print(Level.CORE_INFO, message);
        instance.write(Level.CORE_INFO, message);
        return message;
    }

    private void write(Level level, String message) {
        if (file == null) {
            print(Level.CORE_WARN, "(Log) Cannot write to log file. Logger may not have been initialized.");
            return;
        }

        try {
            FileWriter writer = new FileWriter(file, true);
            writer.write(String.format("%s[%s] %s: %s%n", level.core ? "[CORE] " : "", level.name,
                    new SimpleDateFormat("HH:mm:ss").format(new Date()), message));
            writer.close();
        } catch (IOException ex) {
            print(Level.CORE_WARN, "(Log) Error inializing a FileWriter.");
        }
    }

    private void print(Level level, String message) {
        System.out.println(String.format("%s%s%s[%s]%s %s", level.colorCode, level.core ? "[CORE] " : "", level.name,
                ANSI_RESET, message));
    }
}
