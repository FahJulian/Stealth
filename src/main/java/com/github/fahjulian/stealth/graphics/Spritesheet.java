package com.github.fahjulian.stealth.graphics;

import java.util.ArrayList;
import java.util.List;

import com.github.fahjulian.stealth.core.resources.IResource;
import com.github.fahjulian.stealth.core.resources.IResourceBlueprint;
import com.github.fahjulian.stealth.graphics.opengl.Texture2D;

public class Spritesheet extends Texture2D implements IResource
{

    private final SpritesheetBlueprint blueprint;
    private final List<Sprite> sprites;

    public Spritesheet(SpritesheetBlueprint blueprint)
    {
        super(blueprint.filePath);

        this.blueprint = blueprint;
        this.sprites = new ArrayList<>();

        // Extract sprites
        for (int y = blueprint.height; y > 0; y--)
        {
            for (int x = 0; x < blueprint.width; x++)
            {
                float x0 = x * (blueprint.spriteWidth + blueprint.padding) / (float) super.getWidth();
                float y1 = y * (blueprint.spriteHeight + blueprint.padding) / (float) super.getHeight();
                float x1 = x0 + blueprint.spriteWidth / (float) super.getWidth();
                float y0 = y1 - blueprint.spriteHeight / (float) super.getHeight();

                float[] textureCoords = new float[] {
                        x1, y1, //
                        x0, y1, //
                        x1, y0, //
                        x0, y0
                };

                sprites.add(new Sprite(this, textureCoords));
            }
        }
    }

    public Sprite getSpriteAt(int x, int y)
    {
        return sprites.get(x + y * blueprint.width);
    }

    public int[] posOf(Sprite sprite)
    {
        final int i = sprites.indexOf(sprite);
        return new int[] {
                i % blueprint.width, //
                i / blueprint.width
        };
    }

    @Override
    public SpritesheetBlueprint getBlueprint()
    {
        return blueprint;
    }

    public static class SpritesheetBlueprint implements IResourceBlueprint<Spritesheet>
    {
        public final String name, filePath;
        public final int width, height; // Number of sprites
        public final int spriteWidth, spriteHeight;
        public final int padding;

        public SpritesheetBlueprint(String name, String filePath, int width, int height, int spriteWidth,
                int spriteHeight, int padding)
        {
            this.name = name;
            this.filePath = filePath;
            this.width = width;
            this.height = height;
            this.spriteWidth = spriteWidth;
            this.spriteHeight = spriteHeight;
            this.padding = padding;
        }

        @Override
        public boolean equals(IResourceBlueprint<Spritesheet> blueprint)
        {
            SpritesheetBlueprint b = (SpritesheetBlueprint) blueprint;
            return name.equals(b.name) && filePath.equals(b.filePath) && width == b.width && height == b.height
                    && spriteWidth == b.spriteWidth && spriteHeight == b.spriteHeight && padding == b.padding;
        }

        @Override
        public String getKey()
        {
            return name;
        }

        @Override
        public Spritesheet create()
        {
            return new Spritesheet(this);
        }

        @Override
        public Class<Spritesheet> getResourceClass()
        {
            return Spritesheet.class;
        }
    }
}
