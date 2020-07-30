package com.github.fahjulian.stealth.core.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public final class Toolbox
{
    private Toolbox()
    {
    }

    public static Map<String, String> toFields(String xml)
    {
        Map<String, String> fields = new HashMap<>();

        try (Scanner scanner = new Scanner(xml))
        {
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                int idx1 = line.indexOf("<"), idx2 = line.indexOf(">");
                String xmlTag = (idx1 != -1 && idx2 != -1) ? line.substring(idx1 + 1, idx2) : "";

                fields.put(xmlTag, Toolbox.stripXmlTags(line, xmlTag));
            }
        }

        return fields;
    }

    public static String toXml(Map<String, String> fields)
    {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, String> field : fields.entrySet())
            sb.append(String.format("<%s>%s</%s>%n", field.getKey(), field.getValue(), field.getKey()));

        return sb.toString();
    }

    public static String toXml(Map<String, String> fields, int indent)
    {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, String> field : fields.entrySet())
            sb.append(String.format("%s<%s>%s</%s>%n", " ".repeat(indent), field.getKey(), field.getValue(),
                    field.getKey()));

        return sb.toString();
    }

    public static String getXmlTag(String line)
    {
        int idx1 = line.indexOf("<"), idx2 = line.indexOf(">");
        return (idx1 != -1 && idx2 != -1) ? line.substring(idx1 + 1, idx2) : null;
    }

    public static float clamp(float value, float min, float max)
    {
        return value > max ? max : value < min ? min : value;
    }

    public static int clamp(int value, int min, int max)
    {
        return value > max ? max : value < min ? min : value;
    }

    public static <T> T[] toArray(List<T> list, T[] target)
    {
        for (int i = 0; i < list.size(); i++)
            target[i] = list.get(i);

        return target;
    }

    public static int[] toArray(List<Integer> list)
    {
        return toArray(list, new int[list.size()]);
    }

    public static int[] toArray(List<Integer> list, int[] target)
    {
        for (int i = 0; i < list.size(); i++)
            target[i] = list.get(i);

        return target;
    }

    public static String toString(Object[] array)
    {
        StringBuilder sb = new StringBuilder();

        sb.append("[");
        for (int i = 0; i < array.length; i++)
        {
            sb.append(array[i].toString());
            if (i != array.length - 2)
                sb.append(", ");
        }
        sb.append("]");

        return sb.toString();
    }

    public static String stripXmlTags(String s, String tagName)
    {
        return s.replace("<" + tagName + ">", "").replace("</" + tagName + ">", "");
    }

    public static <T> int indexOf(T[] array, T obj)
    {
        for (int i = 0; i < array.length; i++)
            if (array[i].equals(obj))
                return i;

        return -1;
    }
}
