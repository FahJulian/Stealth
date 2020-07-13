package com.github.fahjulian.stealth.graphics.tilemap;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.github.fahjulian.stealth.core.util.Log;
import com.github.fahjulian.stealth.core.util.Toolbox;
import com.github.fahjulian.stealth.graphics.Sprite;
import com.github.fahjulian.stealth.graphics.Spritesheet;
import com.github.fahjulian.stealth.graphics.opengl.Texture2D;

public class FileHandler
{
    private static final String FILE_EXTENSION = ".stealthMap.xml";

    public static Data loadMapDataFromFile(String filePath)
    {
        int width = -1, height = -1;
        float tileSize = -1.0f, posZ = -1.0f;
        String name = "StealthMap";
        final List<Texture2D> textures = new ArrayList<>();
        final List<Tile> tiles = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(filePath)))
        {
            while (scanner.hasNextLine())
            {
                final String line = scanner.nextLine().replace(" ", "");

                int idx1 = line.indexOf("<"), idx2 = line.indexOf(">");
                String xmlTag = (idx1 != -1 && idx2 != -1) ? line.substring(idx1 + 1, idx2) : "";
                switch (xmlTag)
                {
                    case "name":
                        name = Toolbox.stripXmlTags(line, "name");
                        break;
                    case "width":
                        width = Integer.valueOf(Toolbox.stripXmlTags(line, "width"));
                        break;
                    case "height":
                        height = Integer.valueOf(Toolbox.stripXmlTags(line, "height"));
                        break;
                    case "posZ":
                        posZ = Float.valueOf(Toolbox.stripXmlTags(line, "posZ"));
                        break;
                    case "tileSize":
                        tileSize = Float.valueOf(Toolbox.stripXmlTags(line, "tileSize"));
                        break;
                    case "texture":
                        textures.add(loadTexture(scanner));
                        // textures.add(new Texture2D(Toolbox.stripXmlTags(line, "texture"))); // TODO:
                        // Add resource pool
                        break;
                    case "tile":
                        tiles.add(loadTile(scanner, textures));
                        // tiles.add(new Tile(
                        // new Sprite(textures.get(Integer.valueOf(Toolbox.stripXmlTags(line,
                        // "tile"))))));
                        break;
                }
            }
        }
        catch (Exception e)
        {
            Log.error("(TileMap) Error loading map: %n%s", e.getMessage());
        }

        Log.info("(TileMap) Succesfully loaded Map from file %s", filePath);

        return new Data(name, width, height, tileSize, posZ, Toolbox.toArray(textures, new Texture2D[textures.size()]),
                Toolbox.toArray(tiles, new Tile[tiles.size()]));
    }

    public static void saveMapDataToFile(Data data, String destinationFolder)
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
            file.append(serializeTexture(texture));
        file.append(String.format("</textures>%n%n"));

        file.append(String.format("<tiles>%n"));
        for (Tile tile : data.tiles)
            file.append(serializeTile(tile, data.textures));
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

    private static Tile loadTile(Scanner scanner, List<Texture2D> textures)
    {
        final String SPRITESHEET = "spritesheet", TEXTURE2D = "texture";
        String textureType = null;
        Texture2D texture = null;
        int sheetX = -1, sheetY = -1;
        boolean finished = false;

        while (!finished)
        {
            final String line = scanner.nextLine().replace(" ", "");

            int idx1 = line.indexOf("<"), idx2 = line.indexOf(">");
            String xmlTag = (idx1 != -1 && idx2 != -1) ? line.substring(idx1 + 1, idx2) : "";
            switch (xmlTag)
            {
                case "spriteType":
                    textureType = Toolbox.stripXmlTags(line, "spriteType");
                    break;
                case "textureIndex":
                    texture = textures.get(Integer.valueOf(Toolbox.stripXmlTags(line, "textureIndex")));
                    assert textureType != null : Log
                            .error("(tilemap.FileHandler) Error loading map: Invalid tag order.");
                    if (textureType.equals(TEXTURE2D))
                        finished = true;
                    break;
                case "sheetX":
                    sheetX = Integer.valueOf(Toolbox.stripXmlTags(line, "sheetX"));
                    break;
                case "sheetY":
                    sheetY = Integer.valueOf(Toolbox.stripXmlTags(line, "sheetY"));
                    finished = true;
                    break;
            }
        }

        if (textureType.equals(SPRITESHEET))
            return new Tile(((Spritesheet) texture).getSpriteAt(sheetX, sheetY));
        else
            return new Tile(new Sprite(texture));
    }

    private static Texture2D loadTexture(Scanner scanner)
    {
        boolean isSpritesheet = false;
        String path = null;
        int width = -1, height = -1;
        int spriteWidth = -1, spriteHeight = -1;
        int padding = -1;

        boolean finished = false;
        while (!finished)
        {
            final String line = scanner.nextLine().replace(" ", "");

            int idx1 = line.indexOf("<"), idx2 = line.indexOf(">");
            String xmlTag = (idx1 != -1 && idx2 != -1) ? line.substring(idx1 + 1, idx2) : "";
            switch (xmlTag)
            {
                case "type":
                    isSpritesheet = Toolbox.stripXmlTags(line, "type").equals("spritesheet");
                    break;
                case "path":
                    path = Toolbox.stripXmlTags(line, "path");
                    if (!isSpritesheet)
                        finished = true;
                    break;
                case "width":
                    width = Integer.valueOf(Toolbox.stripXmlTags(line, "width"));
                    break;
                case "height":
                    height = Integer.valueOf(Toolbox.stripXmlTags(line, "height"));
                    break;
                case "spriteWidth":
                    spriteWidth = Integer.valueOf(Toolbox.stripXmlTags(line, "spriteWidth"));
                    break;
                case "spriteHeight":
                    spriteHeight = Integer.valueOf(Toolbox.stripXmlTags(line, "spriteHeight"));
                    break;
                case "padding":
                    padding = Integer.valueOf(Toolbox.stripXmlTags(line, "padding"));
                    finished = true;
                    break;
            }
        }

        if (isSpritesheet)
            return new Spritesheet(path, width, height, spriteWidth, spriteHeight, padding);
        else
            return new Texture2D(path);
    }

    private static String serializeTile(Tile tile, Texture2D[] textures)
    {
        final String SPRITESHEET = "spritesheet", TEXTURE2D = "texture";
        String textureType = tile.getSprite().getTexture() instanceof Spritesheet ? SPRITESHEET : TEXTURE2D;

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("    <tile>%n"));
        sb.append(String.format("        <spriteType>%s</spriteType>%n", textureType));
        sb.append(String.format("        <textureIndex>%d</textureIndex>%n",
                Toolbox.indexOf(textures, tile.getSprite().getTexture())));

        if (textureType.equals(SPRITESHEET))
        {
            int[] sheetPos = ((Spritesheet) tile.getSprite().getTexture()).posOf(tile.getSprite());
            sb.append(String.format("        <sheetX>%d</sheetX>%n", sheetPos[0]));
            sb.append(String.format("        <sheetY>%d</sheetY>%n", sheetPos[1]));
        }

        sb.append(String.format("    </tile>%n"));

        return sb.toString();
    }

    private static String serializeTexture(Texture2D texture)
    {
        boolean isSpritesheet = texture instanceof Spritesheet;

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("    <texture>%n"));
        sb.append(String.format("        <type>%s</type>%n", isSpritesheet ? "spritesheet" : "texture"));
        sb.append(String.format("        <path>%s</path>%n", texture.getFilePath()));

        if (isSpritesheet)
        {
            Spritesheet spritesheet = (Spritesheet) texture;
            sb.append(String.format("        <width>%d</width>%n", spritesheet.getWidth()));
            sb.append(String.format("        <height>%d</height>%n", spritesheet.getHeight()));
            sb.append(String.format("        <spriteWidth>%d</spriteWidth>%n", spritesheet.getSpriteWidth()));
            sb.append(String.format("        <spriteHeight>%d</spriteHeight>%n", spritesheet.getSpriteHeight()));
            sb.append(String.format("        <padding>%d</padding>%n", spritesheet.getPadding()));
        }

        sb.append(String.format("    </texture>%n"));

        return sb.toString();
    }
}
