package com.github.fahjulian.stealth.graphics;

import com.github.fahjulian.stealth.core.resources.IResource;
import com.github.fahjulian.stealth.core.resources.IResourceBlueprint;
import com.github.fahjulian.stealth.graphics.opengl.Texture2D;

public class Texture extends Texture2D implements IResource
{
    private final TextureBlueprint blueprint;

    public Texture(TextureBlueprint blueprint)
    {
        super(blueprint.filePath);
        this.blueprint = blueprint;
    }

    @Override
    public int getWidth()
    {
        return super.getWidth();
    }

    @Override
    public int getHeight()
    {
        return super.getHeight();
    }

    @Override
    public TextureBlueprint getBlueprint()
    {
        return blueprint;
    }

    public static class TextureBlueprint implements IResourceBlueprint<Texture>
    {
        public final String name, filePath;

        public TextureBlueprint(String name, String filePath)
        {
            this.name = name;
            this.filePath = filePath;
        }

        @Override
        public boolean equals(IResourceBlueprint<Texture> blueprint)
        {
            TextureBlueprint b = (TextureBlueprint) blueprint;
            return b.filePath == this.filePath;
        }

        @Override
        public String getKey()
        {
            return name;
        }

        @Override
        public Texture create()
        {
            return new Texture(this);
        }

        @Override
        public Class<Texture> getResourceClass()
        {
            return Texture.class;
        }
    }
}
