package com.github.fahjulian.stealth.graphics.tilemap;

import com.github.fahjulian.stealth.core.util.Log;

public class TileMap
{
    private final Data data;
    private final Model model;

    /**
     * Create a map from given map data
     * 
     * @param data
     *                 The data to construct the map of
     */
    public TileMap(Data data)
    {
        assert data.textures.length == data.width * data.height : Log
                .error("(TileMap) Cant create Tile Map: The amount of textures does not fit to the size of the map.");
        assert data.textures.length > 16 : Log
                .error("(TileMap) Cant create Tile Map: Maximum amount of Textures is 16.");

        this.data = data;
        this.model = new Model(data);
    }

    /**
     * Create a map from a .stealthMap.xml file
     * 
     * @param filePath
     *                     The full path to the file
     */
    public TileMap(String filePath)
    {
        this.data = FileHandler.loadMapDataFromFile(filePath);
        this.model = new Model(data);
    }

    public void saveToFile(String destinationFolder)
    {
        FileHandler.saveMapDataToFile(data, destinationFolder);
    }

    public Model getModel()
    {
        return model;
    }

    public Data getData()
    {
        return data;
    }
}
