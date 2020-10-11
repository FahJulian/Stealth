package com.github.fahjulian.stealth.core.event;

@FunctionalInterface
public interface IEventListener<E extends Event> {
    void onEvent(E event);
}
