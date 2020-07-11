package com.github.fahjulian.stealth.core.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Singleton for printing log message and saving them to a .log file
 */
public final class Log
{
    private static final String ANSI_RESET = "\u001B[0m", ANSI_RED = "\033[1;31m", ANSI_GREEN = "\033[1;32m",
            ANSI_YELLOW = "\033[1;33m", ANSI_BLUE = "\033[1;34m";

    private File file;
    private boolean debug;

    private static Log instance;

    private static enum Level
    {
        INFO("INFO", ANSI_GREEN), WARNING("WARNING", ANSI_YELLOW), ERROR("ERROR", ANSI_RED), DEBUG("DEBUG", ANSI_BLUE);

        public String name, colorCode;

        private Level(String name, String colorCode)
        {
            this.name = name;
            this.colorCode = colorCode;
        }
    }

    private Log()
    {
    }

    private static Log get()
    {
        if (instance == null)
            instance = new Log();

        return instance;
    }

    /**
     * Initialize the logging system
     * 
     * @param fileDir
     *                    The directory to save .log files to
     * @param debug
     *                    Whether or not to print debug messages
     */
    public static void init(String fileDir, boolean debug)
    {
        try
        {
            String date = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date());
            get().file = new File(fileDir + date + ".log");

            new File(fileDir).mkdirs();
            get().file.createNewFile();
        }
        catch (Exception ex)
        {
            get().print(Level.ERROR, "(Log) Fatal error initializing log file.");
        }

        get().debug = debug;
        Log.info("(Log) Iniitalized logger.");
    }

    /**
     * Log an info message formatted by the java string formatter
     * 
     * @param message
     *                    The message to log
     * @param args
     *                    Arguments to pass to the jave formatter
     */
    public static String info(String message, Object... args)
    {
        message = String.format(message, args);
        get().write(Level.INFO, message);
        get().print(Level.INFO, message);

        return message;
    }

    /**
     * Log a warning message formatted by the java string formatter
     * 
     * @param message
     *                    The message to log
     * @param args
     *                    Arguments to pass to the jave formatter
     */
    public static String warn(String message, Object... args)
    {
        message = String.format(message, args);
        get().write(Level.WARNING, message);
        get().print(Level.WARNING, message);

        return message;
    }

    /**
     * Log an error message formatted by the java string formatter
     * 
     * @param message
     *                    The message to log
     * @param args
     *                    Arguments to pass to the jave formatter
     */
    public static String error(String message, Object... args)
    {
        message = String.format(message, args);
        get().write(Level.ERROR, message);
        get().print(Level.ERROR, message);

        return message;
    }

    /**
     * Log a debug message formatted by the java string formatter
     * 
     * @param message
     *                    The message to log
     * @param args
     *                    Arguments to pass to the jave formatter
     */
    public static String debug(String message, Object... args)
    {
        message = String.format(message, args);
        if (get().debug)
        {
            get().write(Level.DEBUG, message);
            get().print(Level.DEBUG, message);
        }

        return message;
    }

    private void write(Level level, String message)
    {
        if (file == null)
        {
            print(Level.WARNING, "(Log) Cannot write to log file. Logger may not have been initialized.");
            return;
        }

        try
        {
            FileWriter writer = new FileWriter(file, true);
            writer.write(String.format("[%s] %s: %s%n", level.name, new SimpleDateFormat("HH:mm:ss").format(new Date()),
                    message));
            writer.close();
        }
        catch (IOException ex)
        {
            print(Level.WARNING, "(Log) Error inializing a FileWriter.");
        }
    }

    private void print(Level level, String message)
    {
        System.out.println(String.format("%s[%s]%s %s", level.colorCode, level.name, ANSI_RESET, message));
    }
}
