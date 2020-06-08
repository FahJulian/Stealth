package com.github.fahjulian.stealth.events.mouse;

public class MouseMovedEvent extends AMouseEvent {

    public MouseMovedEvent(float x, float y) {
		super(x, y);
		dispatch();
    }

	@Override
	public String toString() {
		return String.format("MouseMovedEvent at (%f, %f)", x, y);
	}
}
