package com.github.fahjulian.stealth.core.entity;

import com.github.fahjulian.stealth.core.resources.IDeserializer;
import com.github.fahjulian.stealth.core.resources.ISerializable;
import com.github.fahjulian.stealth.core.util.Log;
import com.github.fahjulian.stealth.core.util.MultiKeyMap;

/**
 * A blueprint to create components from. Should be implemented as an inner
 * class by all components
 */
public abstract class AbstractComponentBlueprint<C extends AbstractComponent> implements ISerializable
{
    private static final MultiKeyMap<String, Class<?>, IDeserializer<? extends AbstractComponentBlueprint<?>>> deserializers = new MultiKeyMap<>();

    protected AbstractComponentBlueprint()
    {
        assert deserializers.containsSecondaryKey(this.getClass()) : Log
                .warn("(AbstractComponentBlueprint) Class %s is not registered with a deserializer");
    }

    /**
     * Create the component with all the given specifications
     * 
     * @return The newly created component
     */
    abstract public C createComponent();

    protected static <C extends AbstractComponentBlueprint<?>> void register(Class<C> blueprintClass,
            IDeserializer<C> deserializer)
    {
        AbstractComponentBlueprint.deserializers.put(blueprintClass.getCanonicalName(), blueprintClass, deserializer);
    }

    public static IDeserializer<? extends AbstractComponentBlueprint<?>> getDeserializer(String blueprintClass)
    {
        return deserializers.getWithPrimaryKey(blueprintClass);
    }
}
