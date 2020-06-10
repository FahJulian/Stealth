package com.github.fahjulian.stealth.core.event;

import java.util.Comparator;

/**
 * An Event Layer Stack holds Event Layers and checks which one of two Event
 * Layers is above the other.
 */
public interface IEventLayerStack extends Comparator<IEventLayer>
{
}
