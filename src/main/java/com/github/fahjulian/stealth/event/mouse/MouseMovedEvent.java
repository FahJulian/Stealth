package com.github.fahjulian.stealth.event.mouse;

public class MouseMovedEvent extends AMouseEvent {

    public MouseMovedEvent(float x, float y) {
		super(x, y);
    }

	@Override
	public String toString() {
		return String.format("MouseMovedEvent at (%f, %f)", x, y);
	}
}
