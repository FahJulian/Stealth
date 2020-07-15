package com.github.fahjulian.stealth.graphics.tilemap;

import com.github.fahjulian.stealth.core.resources.IResourceBlueprint;

public class TileMapBlueprint implements IResourceBlueprint<TileMap>
{
    public final String name, filePath;
    public final int width, height;
    public final float tileSize;
    public final float posZ;

    TileMapBlueprint(String name, String filePath, int width, int height, float tileSize, float posZ)
    {
        this.name = name;
        this.filePath = filePath;
        this.width = width;
        this.height = height;
        this.tileSize = tileSize;
        this.posZ = posZ;
    }

    public static TileMapBlueprint loadFromFile(String filePath)
    {
        return FileHandler.loadBlueprintFromFile(filePath);
    }

    /*
     * TODO: FOR MAP CREATION -> MOVE IMPLEMENTATION TO TileMap
     * 
     * public TileMapBlueprint(String name, int width, int height, float tileSize,
     * float posZ, Tile[] tiles) { this.name = name; this.width = width; this.height
     * = height; this.tileSize = tileSize; this.posZ = posZ; this.tiles = tiles;
     * 
     * final List<Texture2D> textures = new ArrayList<>(); for (Tile tile : tiles)
     * if (!textures.contains(tile.getSprite().getTexture()))
     * textures.add(tile.getSprite().getTexture());
     * 
     * this.textures = Toolbox.toArray(textures, new Texture2D[textures.size()]); }
     */

    @Override
    public boolean equals(IResourceBlueprint<TileMap> blueprint)
    {
        TileMapBlueprint b = (TileMapBlueprint) blueprint;
        return name.equals(b.name) && width == b.width && height == b.height && tileSize == b.tileSize
                && posZ == b.posZ;
    }

    @Override
    public String getKey()
    {
        return name;
    }

    @Override
    public TileMap create()
    {
        return new TileMap(this);
    }

    @Override
    public Class<? extends TileMap> getResourceClass()
    {
        return TileMap.class;
    }
}
