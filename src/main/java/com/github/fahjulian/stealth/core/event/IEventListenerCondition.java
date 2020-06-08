package com.github.fahjulian.stealth.core.event;

@FunctionalInterface
public interface IEventListenerCondition {
    
    boolean shouldEventListenerBeCalled();
    
}
