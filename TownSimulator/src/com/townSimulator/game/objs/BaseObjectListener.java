package com.townSimulator.game.objs;

public interface BaseObjectListener {
	public void objBeTouchDown(BaseObject obj);
	public void objBeTouchUp(BaseObject obj);
	public void objBeTouchDragged(BaseObject baseObject, float x, float y, float deltaX, float deltaY);
}
