package com.github.fahjulian.stealth.graphics.tilemap;

import com.github.fahjulian.stealth.core.util.Log;

public class TileMap
{
    private static final String FILE_EXTENSION = ".stealthMap.xml";

    private final MapData data;
    private final TileMapModel model;

    /**
     * Create a map from given map data
     * 
     * @param data
     *                 The data to construct the map of
     */
    public TileMap(MapData data)
    {
        assert data.textures.length == data.width * data.height : Log
                .error("(TileMap) Cant create Tile Map: The amount of textures does not fit to the size of the map.");
        assert data.textures.length > 16 : Log
                .error("(TileMap) Cant create Tile Map: Maximum amount of Textures is 16.");

        this.data = data;
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
        this.data = TileMapFileHandler.loadMapDataFromFile(filePath);
        this.model = new TileMapModel(data);
    }

    public void saveToFile(String destinationFolder)
    {
        TileMapFileHandler.saveMapDataToFile(data, destinationFolder);
    }

    public TileMapModel getModel()
    {
        return model;
    }

    public MapData getData()
    {
        return data;
    }
}
