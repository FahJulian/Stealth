package com.github.fahjulian.stealth.ui;

import static com.github.fahjulian.stealth.ui.constraint.Type.PIXELS;

import com.github.fahjulian.stealth.graphics.IMaterial;
import com.github.fahjulian.stealth.ui.constraint.UIConstraint;

public class UIBorder
{
    private final IMaterial material;
    private final UIConstraint width, height;

    public UIBorder(IMaterial material, UIConstraint width, UIConstraint height)
    {
        this.material = material;
        this.width = width;
        this.height = height;
    }

    public UIBorder(IMaterial material, UIConstraint size)
    {
        this(material, size, size);
    }

    public UIBorder(IMaterial material, float width, float height)
    {
        this(material, new UIConstraint(PIXELS, width), new UIConstraint(PIXELS, height));
    }

    public UIBorder(IMaterial material, float size)
    {
        this(material, new UIConstraint(PIXELS, size));
    }

    public IMaterial getMaterial()
    {
        return material;
    }

    public UIConstraint getWidth()
    {
        return width;
    }

    public UIConstraint getHeight()
    {
        return height;
    }
}
