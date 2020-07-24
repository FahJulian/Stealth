package com.github.fahjulian.stealth.ui;

import com.github.fahjulian.stealth.ui.UIConstraint.Type;

public class UIConstraints
{
    private UIConstraint x, y, width, height;

    public UIConstraints()
    {
    }

    public UIConstraints(UIConstraint x, UIConstraint y, UIConstraint width, UIConstraint height)
    {
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);
    }

    public UIConstraints(float x, float y, float width, float height)
    {
        this(Type.PIXELS, x, y, width, height);
    }

    public UIConstraints(Type type, float x, float y, float width, float height)
    {
        this(new UIConstraint(type, x), new UIConstraint(type, y), new UIConstraint(type, width),
                new UIConstraint(type, height));
    }

    public void setX(UIConstraint x)
    {
        this.x = x;
    }

    public void setY(UIConstraint y)
    {
        this.y = y;
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

    public UIConstraint getWidth()
    {
        return width;
    }

    public UIConstraint getHeight()
    {
        return height;
    }
}
