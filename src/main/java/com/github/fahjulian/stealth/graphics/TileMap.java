package com.github.fahjulian.stealth.graphics;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.github.fahjulian.stealth.core.util.Log;
import com.github.fahjulian.stealth.core.util.Toolbox;
import com.github.fahjulian.stealth.graphics.models.TileMapModel;
import com.github.fahjulian.stealth.graphics.models.TileMapModel.MapData;
import com.github.fahjulian.stealth.graphics.opengl.Texture2D;

public class TileMap
{
    private static final String FILE_EXTENSION = ".stealthMap.xml";

    private final String name;
    private final MapData data;
    private final TileMapModel model;

    /**
     * Create a tileMap from an array of Textures
     * 
     * @param name
     *                         The name of the map
     * @param width
     *                         The width (number of tiles) of the map
     * @param height
     *                         The height (number of tiles) of the map
     * @param tileSize
     *                         The size of each tile on the map
     * @param posZ
     *                         The z-Position of the map
     * @param tileTextures
     *                         The textures of each tile, starting from the top-left
     */
    public TileMap(String name, int width, int height, float tileSize, float posZ, Texture2D[] tileTextures)
    {
        this.name = name.replace(" ", "");

        if (!(tileTextures.length == width * height))
        {
            Log.error("(TileMap) Cant create Tile Map: The amount of textures does not fit to the size of the map.");
            this.data = null;
            this.model = null;
            return;
        }

        final int[] textureIndices = new int[width * height];
        final List<Texture2D> textures = new ArrayList<>();

        for (int i = 0; i < tileTextures.length; i++)
        {
            Texture2D texture = tileTextures[i];
            if (!textures.contains(texture))
                textures.add(texture);

            textureIndices[i] = textures.indexOf(texture);
        }

        if (textures.size() > 16)
        {
            Log.error("(TileMap) Cant create Tile Map: Maximum amount of Textures is 16.");
            this.data = null;
            this.model = null;
            return;
        }

        final Texture2D[] textureArray = new Texture2D[textures.size()];
        for (int i = 0; i < textures.size(); i++)
            textureArray[i] = textures.get(i);

        this.data = new MapData(width, height, tileSize, posZ, textureArray, textureIndices);
        this.model = new TileMapModel(data);
    }

    /**
     * Create a map from a .stealthMap.xml file
     * 
     * @param filePath
     *                     The full path to the file
     */
    public TileMap(String filePath)
    {
        this.name = new File(filePath).getAbsolutePath().replace(FILE_EXTENSION, "");
        this.data = loadFromFile(filePath);
        this.model = new TileMapModel(data);
    }

    public void saveToFile(String folder)
    {
        StringBuilder text = new StringBuilder();

        text.append(String.format("<width>%d</width>%n", data.width));
        text.append(String.format("<height>%d</height>%n", data.height));
        text.append(String.format("<tileSize>%f</tileSize>%n", data.tileSize));

        for (Texture2D texture : data.textures)
            if (texture != null)
                text.append(String.format("<texture>%s</texture>%n", texture.toString()));

        text.append(String.format("<map>%n"));
        for (int y = 0; y < data.height; y++)
        {
            text.append(String.format("    <row>%n"));
            for (int x = 0; x < data.width; x++)
            {
                text.append(String.format("        <col>%d</col>%n", data.textureIndices[x + y * data.width]));
            }
            text.append(String.format("    </row>%n"));
        }
        text.append(String.format("</map>%n"));

        try
        {
            new File(folder).mkdirs();
            File file = new File(folder + name + FILE_EXTENSION);
            FileWriter writer = new FileWriter(file);
            writer.write(text.toString());
            writer.close();
            Log.info("(TileSet) Saved map to file %s.", file);
        }
        catch (IOException ex)
        {
            Log.error("(TileSet) Could not save map to file.");
        }
    }

    private MapData loadFromFile(String filePath)
    {
        MapData data = new MapData();
        List<Texture2D> textures = new ArrayList<>();
        List<Integer> textureIndices = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(filePath)))
        {
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine().replace(" ", "");
                if (line.startsWith("<width>"))
                    data.width = Integer.valueOf(line.replace("<width>", "").replace("</width>", ""));
                else if (line.startsWith("<height>"))
                    data.height = Integer.valueOf(line.replace("<height>", "").replace("</height>", ""));
                else if (line.startsWith("<tileSize>"))
                    data.tileSize = Float.valueOf(line.replace("<tileSize>", "").replace("</tileSize>", ""));
                else if (line.startsWith("<texture>"))
                    textures.add(new Texture2D(line.replace("<texture>", "").replace("</texture>", "")));
                else if (line.startsWith("<col>"))
                    textureIndices.add(Integer.valueOf(line.replace("<col>", "").replace("</col>", "")));
            }
        }
        catch (Exception ex)
        {
            Log.error("(TileMap) Error loading map: %n%s", ex.getMessage());
        }

        data.textures = Toolbox.toArray(textures, new Texture2D[textures.size()]);
        data.textureIndices = Toolbox.toArray(textureIndices);

        Log.info(Toolbox.toString(data.textures));
        Log.info("(TileMap) Loaded map %s", this.name);
        return data;
    }

    TileMapModel getModel()
    {
        return model;
    }

    public MapData getData()
    {
        return data;
    }
}
