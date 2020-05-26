package com.github.fahjulian.stealth.entity;

/**
 * Abstract class subclassed by all entity components
 */
public abstract class AComponent {

    protected Entity entity;

    /**
     * Initializes the Component. Sets up event listeners.
     */
    abstract public void init();
    abstract public IComponentType getType();
    abstract public String toString();

    /**
     * Set the Entity of the component. Should only be done once.
     * @param entity The Entity to pass
     */
    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }
}
