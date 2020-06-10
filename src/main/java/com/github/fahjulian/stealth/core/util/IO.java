package com.github.fahjulian.stealth.core.util;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Wrapper for Java IO to load files.
 */
public final class IO
{
    /**
     * Load the content of a File
     * 
     * @param filepath
     *                     The path of the File to load from
     * @return The content of the File as a String
     */
    public static String loadResource(String filepath)
    {
        String result = null;
        try (Scanner scanner = new Scanner(new File(filepath), "UTF-8"))
        {
            result = scanner.useDelimiter("\\A").next();
        }
        catch (IOException ex)
        {
            Log.error("(IO) Error loading File %s: %s", filepath, ex.getMessage());
        }
        return result;
    }
}
