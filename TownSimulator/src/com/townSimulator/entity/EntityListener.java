package com.townSimulator.entity;

public interface EntityListener {
	public void objBeTouchDown(Entity obj);
	public void objBeTouchUp(Entity obj);
	public void objBeTouchDragged(Entity baseObject, float x, float y, float deltaX, float deltaY);
}
