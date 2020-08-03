package com.github.fahjulian.stealth.core.resources;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.github.fahjulian.stealth.core.util.IO;
import com.github.fahjulian.stealth.core.util.Log;
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

    public static <C extends ISerializable> void registerDeserializer(Class<C> serializableClass,
            IDeserializer<C> deserializer)
    {
        deserializers.put(serializableClass.getCanonicalName(), serializableClass, deserializer);
    }

    public static <S extends ISerializable> S deserializeFromFile(String filePath)
    {
        Log.info("(SerializablePool) Deserializing from file %s", filePath);
        return SerializablePool.<S>deserialize(IO.loadResource(filePath));
    }

    @SuppressWarnings("unchecked")
    public static <S extends ISerializable> S deserialize(String xml)
    {
        List<String> fieldsXml = new ArrayList<>();
        String serializableClass = scanXml(xml, fieldsXml);

        if (serializableClass == null || fieldsXml == null)
            return null;

        try
        {
            IDeserializer<S> deserializer = (IDeserializer<S>) deserializers.getWithPrimaryKey(serializableClass);
            return deserializer != null ? getLoaded(deserializer.deserialize(Toolbox.toFields(fieldsXml))) : null;
        }
        catch (Exception e)
        {
            Log.error("(SerializablePool) Error deserializing.");
            return null;
        }
    }

    public static void serializeToFile(ISerializable serializable, String filePath)
    {
        try (FileWriter writer = new FileWriter(new File(filePath)))
        {
            Log.info("(SerializablePool) Serializing %s to %s.", serializable, filePath);
            writer.write(serialize(serializable, 0));
        }
        catch (Exception e)
        {
            Log.error("(SerializablePool) Error serializing %s to file %s", serializable, filePath);
        }
    }

    public static String serialize(ISerializable serializable)
    {
        return SerializablePool.serialize(serializable, 0);
    }

    public static String serialize(ISerializable serializable, int indentLevel)
    {
        getLoaded(serializable);
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s<%s>%n", " ".repeat(indentLevel * 4), "serializable"));
        appendFieldsToStringBuilder(sb, getFields(serializable), indentLevel);
        sb.append(String.format("%s</%s>%n", " ".repeat(indentLevel * 4), "serializable"));
        return sb.toString();
    }

    @SuppressWarnings("unchecked")
    public static <S extends ISerializable> S getLoaded(S serializable)
    {
        String serializableClass = serializable.getClass().getCanonicalName();
        Map<String, ISerializable> loadedMap = loadedSerializables.get(serializableClass);
        if (loadedMap == null)
            loadedSerializables.put(serializableClass, loadedMap = new HashMap<>());

        String key = serializable.getUniqueKey();
        S loaded = key != null ? (S) loadedMap.get(key.replace(" ", "")) : null;

        if (loaded == null)
        {
            if (key != null)
                loadedMap.put(key.replace(" ", ""), serializable);
            try
            {
                Log.info("(SerializablePool) Loading %s.", serializable);
                serializable.load();
                return serializable;
            }
            catch (Exception e)
            {
                Log.error("(SerializablePool) Exception occured loading %s.", serializable);
                return null;
            }
        }
        else
        {
            return loaded;
        }
    }

    private static Map<String, Object> getFields(ISerializable serializable)
    {
        Map<String, Object> fields = new LinkedHashMap<>();
        fields.put("class", serializable.getClass().getCanonicalName());
        serializable.serialize(fields);
        return fields;
    }

    private static void appendFieldsToStringBuilder(StringBuilder sb, Map<String, Object> fields, int indentLevel)
    {
        for (Map.Entry<String, Object> field : fields.entrySet())
        {
            String name = field.getKey();
            Object obj = field.getValue();
            String indent = " ".repeat((indentLevel + 1) * 4);

            if (obj instanceof ISerializable)
            {
                String serialized = serialize((ISerializable) obj, indentLevel + 2);
                sb.append(String.format("%s<%s>%n%s%s</%s>%n", indent, name, serialized, indent, name));
            }
            else
            {
                sb.append(String.format("%s<%s>%s</%s>%n", indent, name, obj, name));
            }
        }
    }

    private static String scanXml(String xml, List<String> fieldsListPointer)
    {
        String serializableClass = null;

        try (Scanner scanner = new Scanner(xml))
        {
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine().replace(" ", "");
                String xmlTag = Toolbox.getXmlTag(line);
                if (xmlTag == null)
                    continue;

                if (line.equals("<" + xmlTag + ">"))
                {
                    if (xmlTag.equals("serializable"))
                        continue;
                    else if (xmlTag.equals("/serializable"))
                        break;
                    else
                        fieldsListPointer.add(extractSerializable(scanner, xmlTag));
                }

                else if (xmlTag.equals("class"))
                    serializableClass = Toolbox.stripXmlTags(line, xmlTag);
                else if (fieldsListPointer != null && !line.equals(""))
                    fieldsListPointer.add(line);
            }
        }

        return serializableClass;
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
}
