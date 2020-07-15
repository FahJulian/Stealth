package com.github.fahjulian.stealth.graphics.tilemap;

import java.util.ArrayList;
import java.util.List;

import com.github.fahjulian.stealth.core.resources.IResource;
import com.github.fahjulian.stealth.core.util.Log;
import com.github.fahjulian.stealth.core.util.Toolbox;
import com.github.fahjulian.stealth.graphics.opengl.Texture2D;

public class TileMap implements IResource
{
    private final TileMapBlueprint blueprint;
    final Texture2D[] textures;
    final Tile[] tiles;
    private final TileMapModel model;

    /**
     * Create a map from given map data
     * 
     * @param blueprint
     *                      The blueprint to construct the map of
     */
    TileMap(TileMapBlueprint blueprint)
    {
        this.blueprint = blueprint;
        this.textures = FileHandler.loadTexturesFromFile(blueprint.filePath);
        this.tiles = FileHandler.loadTilesFromFile(blueprint.filePath, textures);
        this.model = new TileMapModel(this);

        assert textures.length == blueprint.width * blueprint.height : Log
                .error("(TileMap) Cant create Tile Map: The amount of textures does not fit to the size of the map.");
        assert textures.length > 16 : Log.error("(TileMap) Cant create Tile Map: Maximum amount of Textures is 16.");
    }

    private TileMap(TileMapBlueprint blueprint, Texture2D[] textures, Tile[] tiles)
    {
        this.blueprint = blueprint;
        this.textures = textures;
        this.tiles = tiles;
        this.model = new TileMapModel(this);
    }

    /**
     * Creates a new map from a given tileset
     * 
     * @param name
     *                     The name of the new map
     * @param tiles
     *                     An array of the tiles of the new map
     * @param width
     *                     The number of tiles horizontally
     * @param height
     *                     The number of tiles vertically
     * @param tileSize
     *                     The size of each tile
     * @param posZ
     *                     The z-Position of the map
     * @return The new map
     */
    public static TileMap createNew(String name, Tile[] tiles, int width, int height, float tileSize, float posZ)
    {
        assert width * height == tiles.length : Log
                .error("(TileMap) Can't create new tile map: Number of tiles does not match size of the map.");

        final List<Texture2D> textures = new ArrayList<>();
        for (Tile tile : tiles)
            if (!textures.contains(tile.getSprite().getTexture()))
                textures.add(tile.getSprite().getTexture());

        return new TileMap(new TileMapBlueprint(name, null, width, height, tileSize, posZ),
                Toolbox.toArray(textures, new Texture2D[textures.size()]), tiles);
    }

    /**
     * Saves the map to an xml file
     * 
     * @param destinationFolder
     *                              Full path to the folder to save the map in
     */
    public void saveToFile(String destinationFolder)
    {
        FileHandler.saveMapToFile(this, destinationFolder);
    }

    public TileMapModel getModel()
    {
        return model;
    }

    @Override
    public TileMapBlueprint getBlueprint()
    {
        return blueprint;
    }
}
