package com.github.fahjulian.stealth.graphics.tilemap;

import java.util.ArrayList;
import java.util.List;

import com.github.fahjulian.stealth.core.util.Toolbox;
import com.github.fahjulian.stealth.graphics.opengl.Texture2D;

class MapData
{
    String name;
    int width, height;
    float tileSize;
    float posZ;
    Texture2D[] textures;
    MapTile[] tiles;

    MapData()
    {
    }

    public MapData(String name, int width, int height, float tileSize, float posZ, Texture2D[] textures,
            MapTile[] tiles)
    {
        this.name = name;
        this.width = width;
        this.height = height;
        this.tileSize = tileSize;
        this.posZ = posZ;
        this.textures = textures;
        this.tiles = tiles;
    }

    public MapData(String name, int width, int height, float tileSize, float posZ, MapTile[] tiles)
    {
        this.name = name;
        this.width = width;
        this.height = height;
        this.tileSize = tileSize;
        this.posZ = posZ;
        this.tiles = tiles;

        final List<Texture2D> textures = new ArrayList<>();
        for (MapTile tile : tiles)
            if (!textures.contains(tile.getTexture()))
                textures.add(tile.getTexture());

        this.textures = Toolbox.toArray(textures, new Texture2D[textures.size()]);
    }
}
