package com.github.fahjulian.stealth.graphics.tilemap;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.github.fahjulian.stealth.core.resources.IResource;
import com.github.fahjulian.stealth.core.resources.ResourcePool;
import com.github.fahjulian.stealth.core.util.Log;
import com.github.fahjulian.stealth.core.util.Toolbox;
import com.github.fahjulian.stealth.graphics.PlainTexture;
import com.github.fahjulian.stealth.graphics.Sprite;
import com.github.fahjulian.stealth.graphics.Spritesheet;
import com.github.fahjulian.stealth.graphics.opengl.AbstractTexture;

public class FileHandler
{
    static class MapInfo
    {
        private MapInfo()
        {
            textures = new ArrayList<>();
        }

        int width, height;
        float tileSize, posZ;
        List<AbstractTexture> textures;
        Tile[] tiles;
    }

    public static MapInfo loadMapInfo(String filePath) throws Exception
    {
        final MapInfo mapInfo = new MapInfo();
        final List<Tile> tiles = new ArrayList<>();
        final Scanner scanner = new Scanner(new File(filePath));

        while (scanner.hasNextLine())
        {
            final String line = scanner.nextLine().replace(" ", "");

            int idx1 = line.indexOf("<"), idx2 = line.indexOf(">");
            String xmlTag = (idx1 != -1 && idx2 != -1) ? line.substring(idx1 + 1, idx2) : "";
            switch (xmlTag)
            {
                case "width":
                    mapInfo.width = Integer.valueOf(Toolbox.stripXmlTags(line, "width"));
                    break;
                case "height":
                    mapInfo.height = Integer.valueOf(Toolbox.stripXmlTags(line, "height"));
                    break;
                case "posZ":
                    mapInfo.posZ = Float.valueOf(Toolbox.stripXmlTags(line, "posZ"));
                    break;
                case "tileSize":
                    mapInfo.tileSize = Float.valueOf(Toolbox.stripXmlTags(line, "tileSize"));
                    break;
                case "texture":
                    mapInfo.textures.add(loadTexture(scanner));
                    break;
                case "tile":
                    tiles.add(loadTile(scanner, mapInfo.textures));
                    break;
            }
        }

        mapInfo.tiles = Toolbox.toArray(tiles, new Tile[tiles.size()]);

        return mapInfo;
    }

    public static void saveMap(int width, int height, float tileSize, float posZ, List<AbstractTexture> textures,
            Tile[] tiles, String filePath) throws Exception
    {
        StringBuilder file = new StringBuilder();

        file.append(String.format("<settings>%n"));
        file.append(String.format("    <width>%d</width>%n", width));
        file.append(String.format("    <height>%d</height>%n", height));
        file.append(String.format("    <tileSize>%f</tileSize>%n", tileSize));
        file.append(String.format("    <posZ>%f</posZ>%n", posZ));
        file.append(String.format("</settings>%n%n"));

        file.append(String.format("<textures>%n"));
        for (AbstractTexture texture : textures)
            file.append(serializeTexture(texture));
        file.append(String.format("</textures>%n%n"));

        file.append(String.format("<tiles>%n"));
        for (Tile tile : tiles)
            file.append(serializeTile(tile, textures));
        file.append(String.format("</tiles>%n"));

        new File(filePath).getParentFile().mkdirs();
        FileWriter writer = new FileWriter(new File(filePath));
        writer.write(file.toString());
        writer.close();
    }

    private static Tile loadTile(Scanner scanner, List<AbstractTexture> textures)
    {
        final String SPRITESHEET = "spritesheet", TEXTURE2D = "texture";
        String textureType = null;
        AbstractTexture texture = null;
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

    private static AbstractTexture loadTexture(Scanner scanner)
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
            return ResourcePool
                    .getOrLoadResource(new Spritesheet(path, width, height, spriteWidth, spriteHeight, padding));
        else
            return ResourcePool.getOrLoadResource(new PlainTexture(path));
    }

    private static String serializeTile(Tile tile, List<AbstractTexture> textures)
    {
        final String SPRITESHEET = "spritesheet", TEXTURE2D = "texture";
        String textureType = tile.getSprite().getTexture() instanceof Spritesheet ? SPRITESHEET : TEXTURE2D;

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("    <tile>%n"));
        sb.append(String.format("        <spriteType>%s</spriteType>%n", textureType));
        sb.append(String.format("        <textureIndex>%d</textureIndex>%n",
                textures.indexOf(tile.getSprite().getTexture())));

        if (textureType.equals(SPRITESHEET))
        {
            int[] sheetPos = ((Spritesheet) tile.getSprite().getTexture()).posOf(tile.getSprite());
            sb.append(String.format("        <sheetX>%d</sheetX>%n", sheetPos[0]));
            sb.append(String.format("        <sheetY>%d</sheetY>%n", sheetPos[1]));
        }

        sb.append(String.format("    </tile>%n"));

        return sb.toString();
    }

    private static String serializeTexture(AbstractTexture texture)
    {
        boolean isSpritesheet = texture instanceof Spritesheet;

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("    <texture>%n"));
        sb.append(String.format("        <type>%s</type>%n", isSpritesheet ? "spritesheet" : "texture"));
        sb.append(String.format("        <path>%s</path>%n", ((IResource) texture).getKey()));

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
