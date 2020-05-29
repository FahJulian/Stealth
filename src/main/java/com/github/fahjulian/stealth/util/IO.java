package com.github.fahjulian.stealth.util;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public final class IO {

    public static String loadResource(String filepath) throws IOException {
        String result = null;
        try (Scanner scanner = new Scanner(new File(filepath), "UTF-8")) {
            result = scanner.useDelimiter("\\A").next();
        }
        return result;
    } 
}
