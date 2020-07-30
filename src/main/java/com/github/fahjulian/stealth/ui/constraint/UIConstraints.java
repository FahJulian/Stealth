package com.github.fahjulian.stealth.ui.constraint;

import static com.github.fahjulian.stealth.ui.constraint.Type.PIXELS;

public class UIConstraints
{
    private UIConstraint x, y, width, height;
    private float z;

    public UIConstraints()
    {
    }

    public UIConstraints(UIConstraint x, UIConstraint y, UIConstraint width, UIConstraint height, float z)
    {
        this.setX(x);
        this.setY(y);
        this.setZ(z);
        this.setWidth(width);
        this.setHeight(height);
    }

    public UIConstraints(float x, float y, float z, float width, float height)
    {
        this(PIXELS, x, y, z, width, height);
    }

    public UIConstraints(Type type, float x, float y, float z, float width, float height)
    {
        this(new UIConstraint(type, x), new UIConstraint(type, y), new UIConstraint(type, width),
                new UIConstraint(type, height), z);
    }

    public void setX(UIConstraint x)
    {
        this.x = x;
    }

    public void setY(UIConstraint y)
    {
        this.y = y;
    }

    public void setZ(float z)
    {
        this.z = z;
    }

    public void setWidth(UIConstraint width)
    {
        this.width = width;
    }

    public void setHeight(UIConstraint height)
    {
        this.height = height;
    }

    public UIConstraint getX()
    {
        return x;
    }

    public UIConstraint getY()
    {
        return y;
    }

    public float getZ()
    {
        return z;
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
