package com.github.fahjulian.stealth.ui.property;

import static com.github.fahjulian.stealth.graphics.Color.BLACK;
import static com.github.fahjulian.stealth.graphics.Color.WHITE;
import static com.github.fahjulian.stealth.ui.constraint.Type.PIXELS;

import java.util.ArrayList;
import java.util.List;

import com.github.fahjulian.stealth.graphics.Color;
import com.github.fahjulian.stealth.ui.constraint.UIConstraint;

public class Types
{
    static final List<Type<?>> allTypes = new ArrayList<>();

    public static final Type<Color> PRIMARY_COLOR = new Type<>(WHITE);
    public static final Type<Color> SECONDARY_COLOR = new Type<>(WHITE);
    public static final Type<Color> HOVER_COLOR = new Type<>(WHITE);
    public static final Type<UIConstraint> BORDER_SIZE = new Type<>(new UIConstraint(PIXELS, 0));
    public static final Type<Color> BORDER_COLOR = new Type<>(BLACK);
    public static final Type<Color> BORDER_HOVER_COLOR = new Type<>(BLACK);
}
