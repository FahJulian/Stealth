package com.github.fahjulian.stealth.graphics.tilemap;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.github.fahjulian.stealth.core.util.Log;
import com.github.fahjulian.stealth.core.util.Toolbox;
import com.github.fahjulian.stealth.graphics.opengl.Texture2D;

public class TileMapFileHandler
{
    private static final String FILE_EXTENSION = ".stealthMap.xml";

    public static MapData loadMapDataFromFile(String filePath)
    {
        int width = -1, height = -1;
        float tileSize = -1.0f, posZ = -1.0f;
        String name = "StealthMap";
        final List<Texture2D> textures = new ArrayList<>();
        final List<MapTile> tiles = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(filePath)))
        {
            while (scanner.hasNextLine())
            {
                final String line = scanner.nextLine().replace(" ", "");

                int idx1 = line.indexOf("<"), idx2 = line.indexOf(">");
                String xmlTag = (idx1 != -1 && idx2 != -1) ? line.substring(idx1, idx2 + 1) : "";
                switch (xmlTag)
                {
                    case "<name>":
                        name = Toolbox.stripXmlTags(line, "name");
                        break;
                    case "<width>":
                        width = Integer.valueOf(Toolbox.stripXmlTags(line, "width"));
                        break;
                    case "<height>":
                        height = Integer.valueOf(Toolbox.stripXmlTags(line, "height"));
                        break;
                    case "<posZ>":
                        posZ = Float.valueOf(Toolbox.stripXmlTags(line, "posZ"));
                        break;
                    case "<tileSize>":
                        tileSize = Float.valueOf(Toolbox.stripXmlTags(line, "tileSize"));
                        break;
                    case "<texture>":
                        textures.add(new Texture2D(Toolbox.stripXmlTags(line, "texture"))); // TODO: Add resource pool
                        break;
                    case "<tile>":
                        tiles.add(new MapTile(textures.get(Integer.valueOf(Toolbox.stripXmlTags(line, "tile")))));
                        break;
                }
            }
        }
        catch (Exception e)
        {
            Log.error("(TileMap) Error loading map: %n%s", e.getMessage());
        }

        Log.info("(TileMap) Succesfully loaded Map from file %s", filePath);

        return new MapData(name, width, height, tileSize, posZ,
                Toolbox.toArray(textures, new Texture2D[textures.size()]),
                Toolbox.toArray(tiles, new MapTile[tiles.size()]));
    }

    public static void saveMapDataToFile(MapData data, String destinationFolder)
    {
        StringBuilder file = new StringBuilder();

        file.append(String.format("<name>%s</name>%n%n", data.name));
        file.append(String.format("<settings>%n"));
        file.append(String.format("    <width>%d</width>%n", data.width));
        file.append(String.format("    <height>%d</height>%n", data.height));
        file.append(String.format("    <tileSize>%f</tileSize>%n", data.tileSize));
        file.append(String.format("</settings>%n%n"));

        file.append(String.format("<textures>%n"));
        for (Texture2D texture : data.textures)
            file.append(String.format("    <texture>%s</texture>%n", texture.getName()));
        file.append(String.format("</texture>%n%n"));

        file.append(String.format("<tiles>%n"));
        for (MapTile tile : data.tiles)
            file.append(
                    String.format("    <tile>%d</tile>%n", Arrays.asList(data.textures).indexOf(tile.getTexture())));
        file.append(String.format("</tiles>%n"));

        try
        {
            new File(destinationFolder).mkdirs();

            String fileName = destinationFolder + data.name.replace(" ", "") + FILE_EXTENSION;
            FileWriter writer = new FileWriter(new File(fileName));
            writer.write(file.toString());
            writer.close();

            Log.info("(TileSet) Succesfully saved map to file %s.", fileName);
        }
        catch (IOException e)
        {
            Log.error("(TileSet) Exception while trying to save map %s: %s", data.name, e.getMessage());
        }
    }
}
