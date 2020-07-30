package com.github.fahjulian.stealth.core.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.github.fahjulian.stealth.core.resources.ISerializable;
import com.github.fahjulian.stealth.core.util.Toolbox;

/**
 * An Entity Blueprint holds information about components and can create a new
 * entity from that information
 */
public class EntityBlueprint implements ISerializable
{
    private final AbstractComponentBlueprint<?>[] initialComponents;

    /**
     * Construct a new Enitiy blueprint
     *
     * @param initialComponents
     *                              Blueprints to create the initial components the
     *                              created entities will have
     */
    public EntityBlueprint(AbstractComponentBlueprint<?>... initialComponents)
    {
        this.initialComponents = initialComponents;
    }

    /**
     * Creates a new entity with all the initial components specified in the
     * constructor of this blueprint
     * 
     * @param name
     *                      The name of the new entity
     * @param transform
     *                      The transform of the new entity
     * 
     * @return The new entity
     */
    public Entity create(String name, Transform transform)
    {
        AbstractComponent[] components = new AbstractComponent[initialComponents.length];
        for (int i = 0; i < components.length; i++)
            components[i] = initialComponents[i].createComponent();

        return new Entity(name, transform, components);
    }

    public static EntityBlueprint deserialize(String xml)
    {
        StringBuilder currentComponentBp = null;
        String currentBlueprintClass = null;
        List<AbstractComponentBlueprint<?>> componentBlueprints = new ArrayList<>();

        try (Scanner scanner = new Scanner(xml))
        {
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                String xmlTag = Toolbox.getXmlTag(line);
                switch (xmlTag)
                {
                case "componentBlueprint":
                    currentComponentBp = new StringBuilder();
                    break;
                case "blueprintClass":
                    currentBlueprintClass = Toolbox.stripXmlTags(line, xmlTag).replace(" ", "");
                    break;
                case "/componentBlueprint":
                    componentBlueprints.add(AbstractComponentBlueprint.getDeserializer(currentBlueprintClass)
                            .deserialize(currentComponentBp.toString()));
                    break;
                default:
                    if (currentComponentBp != null)
                        currentComponentBp.append(line);
                    break;
                }
            }
        }

        return new EntityBlueprint(
                componentBlueprints.toArray(new AbstractComponentBlueprint[componentBlueprints.size()]));
    }

    public String serialize()
    {
        StringBuilder sb = new StringBuilder(String.format("<entityBlueprint>%n"));

        for (AbstractComponentBlueprint<?> componentBlueprint : this.initialComponents)
        {
            sb.append(String.format("    <componentBlueprint>%n"));
            sb.append(String.format("        <blueprintClass>%s</blueprintClass>%n",
                    componentBlueprint.getClass().getCanonicalName()));
            sb.append(componentBlueprint.serialize());
            sb.append(String.format("    </componentBlueprint>%n"));
        }

        sb.append("</entityBlueprint>");
        return sb.toString();
    }
}
