package com.github.fahjulian.stealth.core.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.github.fahjulian.stealth.core.util.MultiKeyMap;
import com.github.fahjulian.stealth.core.util.Toolbox;

public final class SerializablePool
{
    private static final MultiKeyMap<String, Class<? extends ISerializable>, IDeserializer<?>> deserializers;
    private static final Map<String, Map<String, ISerializable>> loadedSerializables;

    static
    {
        deserializers = new MultiKeyMap<>();
        loadedSerializables = new HashMap<>();
    }

    public static <C extends ISerializable> void register(Class<C> serializableClass, IDeserializer<C> deserializer)
    {
        deserializers.put(serializableClass.getCanonicalName(), serializableClass, deserializer);
    }

    private static String extractSerializable(Scanner scanner, String name)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("<" + name + ">%n"));

        String line = null;
        while (scanner.hasNextLine() && !(line = scanner.nextLine().replace(" ", "")).equals("</" + name + ">"))
        {
            sb.append(String.format(line + "%n"));
        }

        sb.append(String.format("</" + name + ">%n"));
        return sb.toString();
    }

    @SuppressWarnings("unchecked")
    public static <C extends ISerializable> C deserialize(String xml)
    {
        List<String> fieldsXml = null;
        String serializableClass = null;
        String uniqueKey = null;

        try (Scanner scanner = new Scanner(xml))
        {
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine().replace(" ", "");
                String xmlTag = Toolbox.getXmlTag(line);

                if (line.equals("<" + xmlTag + ">"))
                {
                    if (xmlTag.equals("serializable") && fieldsXml == null)
                        fieldsXml = new ArrayList<>();
                    else if (xmlTag.equals("/serializable"))
                        break;
                    else
                        fieldsXml.add(extractSerializable(scanner, xmlTag));
                }

                else if (xmlTag.equals("type"))
                    serializableClass = Toolbox.stripXmlTags(line, xmlTag);
                else if (xmlTag.equals("uniqueKey"))
                    uniqueKey = Toolbox.stripXmlTags(line, xmlTag);
                else if (fieldsXml != null && !line.equals(""))
                    fieldsXml.add(line);
            }
        }

        if (serializableClass == null || fieldsXml == null || uniqueKey == null)
            return null;

        Map<String, ISerializable> loaded = loadedSerializables.get(serializableClass);
        if (loaded == null)
        {
            loaded = new HashMap<>();
            loadedSerializables.put(serializableClass, loaded);
        }

        C c = (C) loaded.get(uniqueKey);
        if (c == null)
        {
            IDeserializer<C> deserializer = (IDeserializer<C>) deserializers.getWithPrimaryKey(serializableClass);
            if (deserializer == null)
                return null;

            c = deserializer.deserialize(Toolbox.toFields(fieldsXml));
            loaded.put(uniqueKey, c);
        }

        return c;
    }

    public static String serialize(ISerializable serializable)
    {
        return SerializablePool.serialize(serializable, 0);
    }

    public static String serialize(ISerializable serializable, int indent)
    {
        String serializableClass = serializable.getClass().getCanonicalName();
        Map<String, ISerializable> loaded = loadedSerializables.get(serializableClass);
        if (loaded == null)
        {
            loaded = new HashMap<>();
            loadedSerializables.put(serializableClass, loaded);
        }
        if (loaded.get(serializable.getUniqueKey()) == null)
            loaded.put(serializable.getUniqueKey().replace(" ", ""), serializable);

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s<%s>%n", " ".repeat(indent * 4), "serializable"));

        Map<String, Object> fields = new LinkedHashMap<>();
        fields.put("type", serializableClass);
        fields.put("uniqueKey", serializable.getUniqueKey());
        serializable.serialize(fields);
        for (Map.Entry<String, Object> field : fields.entrySet())
        {
            if (field.getValue() instanceof ISerializable)
            {
                sb.append(String.format("%s<%s>%n%s%s</%s>%n", " ".repeat((indent + 1) * 4), field.getKey(),
                        serialize((ISerializable) field.getValue(), indent + 2), " ".repeat((indent + 1) * 4),
                        field.getKey()));
            }
            else
            {
                sb.append(String.format("%s<%s>%s</%s>%n", " ".repeat((indent + 1) * 4), field.getKey(),
                        field.getValue(), field.getKey()));
            }
        }

        sb.append(String.format("%s</%s>%n", " ".repeat(indent * 4), "serializable"));

        return sb.toString();
    }
}
