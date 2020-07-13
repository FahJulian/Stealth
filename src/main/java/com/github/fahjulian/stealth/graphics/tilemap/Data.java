package com.github.fahjulian.stealth.graphics.tilemap;

import java.util.ArrayList;
import java.util.List;

import com.github.fahjulian.stealth.core.util.Toolbox;
import com.github.fahjulian.stealth.graphics.opengl.Texture2D;

public class Data
{
    final String name;
    final int width, height;
    final float tileSize;
    final float posZ;
    final Texture2D[] textures;
    final Tile[] tiles;

    public Data(String name, int width, int height, float tileSize, float posZ, Texture2D[] textures, Tile[] tiles)
    {
        this.name = name;
        this.width = width;
        this.height = height;
        this.tileSize = tileSize;
        this.posZ = posZ;
        this.textures = textures;
        this.tiles = tiles;
    }

    public Data(String name, int width, int height, float tileSize, float posZ, Tile[] tiles)
    {
        this.name = name;
        this.width = width;
        this.height = height;
        this.tileSize = tileSize;
        this.posZ = posZ;
        this.tiles = tiles;

        final List<Texture2D> textures = new ArrayList<>();
        for (Tile tile : tiles)
            if (!textures.contains(tile.getSprite().getTexture()))
                textures.add(tile.getSprite().getTexture());

        this.textures = Toolbox.toArray(textures, new Texture2D[textures.size()]);
    }
}
