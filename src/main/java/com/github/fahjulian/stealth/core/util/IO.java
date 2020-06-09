package com.github.fahjulian.stealth.core.util;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public final class IO
{
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
