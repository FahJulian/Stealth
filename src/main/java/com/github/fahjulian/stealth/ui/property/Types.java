package com.github.fahjulian.stealth.ui.property;

import java.util.ArrayList;
import java.util.List;

import com.github.fahjulian.stealth.graphics.IMaterial;
import com.github.fahjulian.stealth.ui.UIBorder;

public class Types
{
    static final List<Type<?>> allTypes = new ArrayList<>();

    public static final Type<IMaterial> PRIMARY_MATERIAL = new Type<>();
    public static final Type<IMaterial> SECONDARY_MATERIAL = new Type<>();
    public static final Type<IMaterial> HOVER_MATERIAL = new Type<>();

    public static final Type<UIBorder> BORDER = new Type<>();
    public static final Type<UIBorder> HOVER_BORDER = new Type<>();
}
